package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ConflictRunTimeException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ReservationContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestUpdateSeatDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseCancelationDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.UserEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.ReservationStatus;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ReservationRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.SeatRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ShowtimeRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class ReservationService implements ReservationContract {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, ShowtimeRepository showtimeRepository, SeatRepository seatRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }


    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto request) {

        try{
            ReservationEntity result = reservationRepository.save(creatReservationEntityMapping(request));

            if (result.getId() == null){
                throw ReservationException.failToSaveOnDB("");
            }

            return mappingFromEntity(result);

        } catch (DataIntegrityViolationException e){
            throw ReservationException.failToSaveOnDB((e.getMessage()));
        }

    }

    @Override
    public ReservationResponseDto updateSeat(ReservationRequestUpdateSeatDto request) {
        ReservationEntity reservation = reservationRepository.findById(request.reservationID()).orElseThrow(() -> NotFoundException.reservationNotExits(request.reservationID()));
        SeatEntity seat = seatRepository.findById(request.seatID()).orElseThrow(NotFoundException::seatNotFound);
        if (reservation.getSeat().getSeatColumn() == seat.getSeatColumn() && reservation.getSeat().getSeatRow() == seat.getSeatRow()){
            throw ConflictRunTimeException.seatAlreadyInUserBySameUser(seat.getSeatRow(), seat.getSeatColumn());
        }
        if (!seatRepository.isSeatFree(reservation.getShowtime().getId(), seat.getId())){ throw ConflictRunTimeException.seatNotAvailable(); }
        reservation.setSeat(seat);
        return mappingFromEntity(reservation);
    }

    @Override
    public ReservationResponseDto getReservation(int id){
        ReservationEntity reservation = reservationRepository.findById(id).orElseThrow(() -> NotFoundException.reservationNotExits(id));
        return mappingFromEntity(reservation);
    }

    @Override
    public ReservationResponseCancelationDto cancelReservetion(int id ) {
        ReservationEntity reservation = reservationRepository.findById(id).orElseThrow(() -> NotFoundException.reservationNotExits(id));
        ReservationResponseCancelationDto cancel = ReservationResponseCancelationDto.builder().id(id).status("SUCCESSFULLY_CANCELED").dateTime(LocalDateTime.now()).build();
        reservationRepository.delete(reservation);
        return cancel;
    }


    private ReservationEntity creatReservationEntityMapping(ReservationRequestDto request){
        UserEntity user = null;
        if (request.email_user() != null && !request.email_user().isBlank()){
            user = userRepository.findByEmail(request.email_user()).orElseThrow(() -> NotFoundException.userEmailNotExists(request.email_user()));
        }

        ShowtimeEntity showtime = showtimeRepository.findById(request.id_showtime()).orElseThrow(() -> NotFoundException.showTimeNotExist(request.id_showtime()));
        isPastLimit(showtime); // throw an exception if showtime already started or gone.

        SeatEntity seat = seatRepository.findById(request.id_seat()).orElseThrow(NotFoundException::seatNotFound);
        if (!showtime.getRoom().getId().equals(seat.getRoom().getId())){
            throw ConflictRunTimeException.seatNotEqualFromShowTime();
        }
        if (!seatRepository.isSeatFree( showtime.getId() ,seat.getId())){
            throw ConflictRunTimeException.seatNotAvailable();
        }

        return ReservationEntity.builder()
                .id(null).user(user).showtime(showtime).seat(seat).status(ReservationStatus.PENDING).build();
    }

    private void isPastLimit(ShowtimeEntity showtime){
        if (showtime.getDateTime().plusMinutes(10).isBefore(LocalDateTime.now())){
            throw ReservationException.showtimeAlredyGone("");
        }
    }

    private ReservationResponseDto mappingFromEntity(ReservationEntity reservation){
         return ReservationResponseDto.builder()
                .id(reservation.getId())
                .movie(reservation.getShowtime().getMovie().getTitle())
                .time(reservation.getShowtime().getDateTime().toString())
                .room(reservation.getShowtime().getRoom().getName())
                .row(reservation.getSeat().getSeatRow())
                .col(reservation.getSeat().getSeatColumn())
                .status(reservation.getStatus().getStatus())
                .build();
    }

}
