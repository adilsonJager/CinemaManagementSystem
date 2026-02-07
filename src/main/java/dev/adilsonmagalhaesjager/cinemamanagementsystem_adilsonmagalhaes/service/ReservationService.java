package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.core.PaymentContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ConflictRunTimeException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ReservationContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.Dto.CheckoutRequestDto;
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
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.mapper.ReservationMapper;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.validator.ReservationValidator;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ReservationService implements ReservationContract {

    private final ReservationRepository reservationRepository;
    private final ReservationValidator reservationValidator;
    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final ReservationMapper reservationMapper;
    private final PaymentContract paymentContract;



    public ReservationService(ReservationRepository reservationRepository, ReservationValidator reservationValidator, UserRepository userRepository, ShowtimeRepository showtimeRepository, SeatRepository seatRepository, ReservationMapper reservationMapper, PaymentContract paymentContract) {
        this.reservationRepository = reservationRepository;
        this.reservationValidator = reservationValidator;
        this.userRepository = userRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.reservationMapper = reservationMapper;

        this.paymentContract = paymentContract;
    }


    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto request) {

        try{
            ReservationEntity result = reservationRepository.save(creatReservationEntityMapping(request));

            if (result.getId() == null){
                throw ReservationException.failToSaveOnDB();
            }

            return reservationMapper.mappingShowtimeToDto(result);

        } catch (DataIntegrityViolationException e){
            throw ReservationException.failToSaveOnDB((e.getMessage()));
        }

    }

    @Override
    public ReservationResponseDto updateSeat(ReservationRequestUpdateSeatDto request) {
        ReservationEntity reservation = reservationRepository.findById(request.reservationID()).orElseThrow(() -> NotFoundException.reservationNotExits(request.reservationID()));
        SeatEntity seat = seatRepository.findById(request.seatID()).orElseThrow(NotFoundException::seatNotFound);
        if (reservation.getSeat().getSeatColumn() == seat.getSeatColumn() && reservation.getSeat().getSeatRow() == seat.getSeatRow()){
            throw ConflictRunTimeException.seatAlreadyInUserBySameUser();
        }
        if (!seatRepository.isSeatFree(reservation.getShowtime().getId(), seat.getId())){ throw ConflictRunTimeException.seatNotAvailable(); }
        reservation.setSeat(seat);
        return reservationMapper.mappingShowtimeToDto(reservation);
    }

    @Override
    public ReservationResponseDto getReservation(int id){
        ReservationEntity reservation = reservationRepository.findById(id).orElseThrow(() -> NotFoundException.reservationNotExits(id));
        return reservationMapper.mappingShowtimeToDto(reservation);
    }

    @Override
    public ReservationResponseCancelationDto cancelReservetion(int id ) {
        ReservationEntity reservation = reservationRepository.findById(id).orElseThrow(() -> NotFoundException.reservationNotExits(id));
        ReservationResponseCancelationDto cancel = ReservationResponseCancelationDto.builder().id(id).status("SUCCESSFULLY_CANCELED").dateTime(LocalDateTime.now()).build();
        reservationRepository.delete(reservation);
        return cancel;
    }


    @Override
    public ReservationResponseDto payment(CheckoutRequestDto request){

            updateEmailAndName(request.name(), request.userEmail(), request.reservationId());
            ReservationEntity reservation = paymentContract.execute(request);
            return reservationMapper.mappingShowtimeToDto(reservation);
    }



    private ReservationEntity creatReservationEntityMapping(ReservationRequestDto request){

        UserEntity user = null;
        if (request.email_user() != null && !request.email_user().isBlank()){
            user = userRepository.findByEmail(request.email_user()).orElseThrow(() -> NotFoundException.userEmailNotExists(request.email_user()));
        }

        ShowtimeEntity showtime = showtimeRepository.findById(request.id_showtime()).orElseThrow(() -> NotFoundException.showTimeNotExist(request.id_showtime()));
        reservationValidator.validateShowtime(showtime);

        SeatEntity seat = seatRepository.findById(request.id_seat()).orElseThrow(NotFoundException::seatNotFound);
        reservationValidator.validateSeatAvailability(seatRepository, showtime.getId(), seat.getId());
        reservationValidator.validatorSeatChoiceEqualRoomShowTime(showtime, seat);

        return ReservationEntity.builder()
                .id(null).user(user).showtime(showtime).seat(seat).status(ReservationStatus.PENDING).build();

    }

    private void updateEmailAndName(String name, String email, int idShowtime){
        ReservationEntity reservation = reservationRepository.findById(idShowtime).orElseThrow(() -> NotFoundException.reservationNotExits(idShowtime));
        UserEntity user = userRepository.findByEmail(email).orElseGet(() -> new UserEntity(null, name, email));

        if (user.getId() == null ){
            user = userRepository.save(user);
        }

        reservation.setUser(user);
        reservationRepository.save(reservation);

    }

}
