package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ReservationContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestDto;
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
import java.util.Optional;

@Transactional
@Service
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


        Optional<UserEntity> user = userRepository.findByEmail(request.email_user());
        ShowtimeEntity showtime = showtimeRepository.findById(request.id_showtime()).orElseThrow(() -> NotFoundException.showTimeNotExist(request.id_showtime()));
        SeatEntity seat = seatRepository.findById(request.id_seat()).orElseThrow(NotFoundException::seatNotFound);
        isPastLimit(showtime);


        ReservationEntity reservation = ReservationEntity.builder()
                .id(null).user(user.orElse(null)).showtime(showtime).seat(seat).status(ReservationStatus.PENDING).build();

        try{
            ReservationEntity result = reservationRepository.save(reservation);

            if (result.getId() == null){
                throw ReservationException.failToSaveOnDB("");
            }

            return mappingFromEntity(result);

        } catch (DataIntegrityViolationException e){
            throw ReservationException.failToSaveOnDB((e.getMessage()));
        }

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
