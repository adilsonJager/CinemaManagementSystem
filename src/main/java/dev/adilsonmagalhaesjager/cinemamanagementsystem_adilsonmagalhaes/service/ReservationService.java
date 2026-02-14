package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.Dto.CheckoutRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.core.PaymentContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ReservationContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ReservationItemsContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ShowtimeContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.UsersContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.UserRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseCancelationDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.UserResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationItemEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.UserEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.ReservationStatus;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationService implements ReservationContract {

    private final ReservationRepository reservationRepository;
    private final PaymentContract paymentService;
    private final UsersContract userService;
    private final ShowtimeContract showtimeService;
    private final ReservationItemsContract serviceItems;

    public ReservationService(ReservationRepository reservationRepository, PaymentContract paymentservice, UsersContract userService, ShowtimeContract showtimeService, ReservationItemsContract serviceItems) {
        this.reservationRepository = reservationRepository;
        this.paymentService = paymentservice;
        this.userService = userService;
        this.showtimeService = showtimeService;
        this.serviceItems = serviceItems;
    }


    @Override
    public ReservationResponseDto createReservation(ReservationRequestDto request) {

        try{
            ReservationEntity reservation = reservationRepository.save(creatReservationEntityMapping(request));
            if (reservation.getId() == null){throw ReservationException.failToSaveOnDB();}
            List<ReservationItemEntity> items = serviceItems.saveItems(request.seats(), reservation);
            reservation.setItems(items);
            ReservationEntity savedReservation = reservationRepository.save(reservation);
            return mappingShowtimeToDto(savedReservation);
        } catch (DataIntegrityViolationException e){
            throw ReservationException.failToSaveOnDB((e.getMessage()));
        }

    }


    @Override
    public ReservationResponseDto getReservation(int id){
        ReservationEntity reservation = reservationRepository.findById(id).orElseThrow(() -> NotFoundException.reservationNotExits(id));
        return mappingShowtimeToDto(reservation);
    }

    @Override
    public ReservationResponseCancelationDto cancelReservetion(int id) {
        ReservationEntity reservation = reservationRepository.findById(id).orElseThrow(() -> NotFoundException.reservationNotExits(id));
        ReservationResponseCancelationDto cancel = ReservationResponseCancelationDto.builder().id(id).status("SUCCESSFULLY_CANCELED").dateTime(LocalDateTime.now()).build();
        reservationRepository.delete(reservation);
        return cancel;
    }

    @Override
    public ReservationResponseDto payment(CheckoutRequestDto request){

        ReservationEntity reservation = paymentService.execute(request);

        if (userService.userExist(request.userEmail()) && reservation.getStatus().equals(ReservationStatus.CONFIRMED)){
            UserResponseDto user = userService.getUserByEmail(request.userEmail());
            reservation.setUser(
                    UserEntity.builder().id(user.id()).name(user.name()).email(user.email()).build()
            );
        } else {
            UserResponseDto response = userService.createUser(UserRequestDto.builder().name(request.name()).email(request.userEmail()).build());
            reservation.setUser(
                    UserEntity.builder().id(response.id()).name(response.name()).email(response.email()).build()
            );
        }

        reservationRepository.save(reservation);
        return mappingShowtimeToDto(reservation);

    }

    private ReservationEntity creatReservationEntityMapping(ReservationRequestDto request){
        UserResponseDto userDto = null;
        if (request.email_user() != null && !request.email_user().isBlank()){
            userDto = userService.getUserByEmail(request.email_user());
        }
        ShowtimeEntity showtime = showtimeService.getShowtimeEntityById(request.id_showtime());
        if (showtime.getDateTime().plusMinutes(10).isBefore(LocalDateTime.now())){ throw ReservationException.showtimeAlredyGone();}
        return ReservationEntity.builder()
                .id(null)
                .user(mappingUserEntityToDto(userDto).orElse(null))
                .showtime(showtime)
                .status(ReservationStatus.PENDING)
                .build();
    }

    private Optional<UserEntity> mappingUserEntityToDto(UserResponseDto dto){
        if (dto == null){
            return Optional.empty();
        }
        return  Optional.of(UserEntity.builder().id(dto.id()).name(dto.name()).email(dto.email()).build());
    }


    private ReservationResponseDto mappingShowtimeToDto(ReservationEntity reservation){
        List<String> seats = (reservation.getItems() == null)
                ? List.of()
                : reservation.getItems().stream()
                .map(item -> "Fila: " + item.getSeat().getSeatRow() + " Col: " + item.getSeat().getSeatColumn())
                .toList();
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .movie(reservation.getShowtime().getMovie().getTitle())
                .time(reservation.getShowtime().getDateTime().toString())
                .room(reservation.getShowtime().getRoom().getName())
                .seats(seats)
                .status(reservation.getStatus().getStatus())
                .build();
    }


}
