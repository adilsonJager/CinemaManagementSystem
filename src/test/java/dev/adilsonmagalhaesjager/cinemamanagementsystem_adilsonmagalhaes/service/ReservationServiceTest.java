package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.*;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.ReservationStatus;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ReservationRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.SeatRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ShowtimeRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ShowtimeRepository showtimeRepository;
    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ReservationService reservationService;


    private ReservationRequestDto requestDto;
    private ShowtimeEntity showtimeEntity;
    private RoomEntity roomEntity;
    private MovieEntity movieEntity;
    private SeatEntity seatEntity;
    private ReservationEntity reservationEntity;
    private ReservationEntity reservationEntityNull;

    @BeforeEach
    void setUp(){

        LocalDateTime now = LocalDateTime.now().plusMonths(1);

        //ShowTime Section
        movieEntity = new MovieEntity(1, "Deu a louca na chapeuzinho", 120);
        roomEntity = new RoomEntity(1, "A", 150);
        showtimeEntity = new ShowtimeEntity(1, movieEntity, roomEntity, now );
        // End

        //Reservation section
        seatEntity = new SeatEntity(184, 1, 1, roomEntity);
        requestDto = new ReservationRequestDto(null, showtimeEntity.getId(), seatEntity.getId());
        reservationEntity =  ReservationEntity.builder().id(1).user(null).showtime(showtimeEntity).seat(seatEntity).status(ReservationStatus.PENDING).build();
        reservationEntityNull =  ReservationEntity.builder().id(null).user(null).showtime(showtimeEntity).seat(seatEntity).status(ReservationStatus.PENDING).build();
    }

    @Test
    @DisplayName("Should create reservation successfully when all data is valid")
    void shouldCreateReservationWithSuccess() {


        when(showtimeRepository.findById(requestDto.id_showtime())).thenReturn(Optional.of(showtimeEntity));
        when(seatRepository.findById(seatEntity.getId())).thenReturn(Optional.of(seatEntity));
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);

        ReservationResponseDto result = reservationService.createReservation(requestDto);

        assertNotNull(result.id());
        assertEquals("Deu a louca na chapeuzinho", result.movie());

        verify(userRepository, times(1)).findByEmail(any());
        verify(showtimeRepository, times(1)).findById(1);
        verify(seatRepository, times(1)).findById(184);
        verify(reservationRepository, times(1)).save(any(ReservationEntity.class));

    }


    @Test
    @DisplayName("Should throw NotFoundException when showtime does not exist")
    void shouldThrowExceptionWhenShowtimeNotFound(){
        when(showtimeRepository.findById(requestDto.id_showtime())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> reservationService.createReservation(requestDto));
        verify(showtimeRepository).findById( requestDto.id_showtime());
    }


    @Test
    @DisplayName("Should throw NotFoundException when seat does not exist")
    void shouldThrowExceptionWhenSeatNotFound(){
        when(showtimeRepository.findById(requestDto.id_showtime())).thenReturn(Optional.of(showtimeEntity));
        when(seatRepository.findById(seatEntity.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reservationService.createReservation(requestDto));
        verify(seatRepository).findById(requestDto.id_seat());
    }

    @Test
    @DisplayName("Should throw ReservationException when database fails to return generated ID")
    void shouldThrowExceptionWhenDatabaseFailsToReturnId(){
        when(showtimeRepository.findById(requestDto.id_showtime())).thenReturn(Optional.of(showtimeEntity));
        when(seatRepository.findById(seatEntity.getId())).thenReturn(Optional.of(seatEntity));
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntityNull);

        assertThrows(ReservationException.class, () -> reservationService.createReservation(requestDto));
        verify(reservationRepository).save(any(ReservationEntity.class));
    }

    @Test
    @DisplayName("Should block reservation when 10-minute tolerance limit is exceeded")
    void shouldBlockReservationWhenPastToleranceLimit(){

        ShowtimeEntity pastShowTime = new ShowtimeEntity(1, movieEntity, roomEntity, LocalDateTime.now().minusMinutes(11));
        when(showtimeRepository.findById(any())).thenReturn(Optional.of(pastShowTime));

        assertThrows(ReservationException.class, () -> reservationService.createReservation(requestDto));
        verifyNoInteractions(seatRepository);
        verifyNoInteractions(reservationRepository);

    }

    @Test
    @DisplayName("Should allow reservation when within the 10-minute tolerance window")
    void shouldAllowReservationWhenWithinToleranceTime(){

        ShowtimeEntity pastShowTime = new ShowtimeEntity(1, movieEntity, roomEntity, LocalDateTime.now().minusMinutes(9));
        when(showtimeRepository.findById(any())).thenReturn(Optional.of(pastShowTime));
        when(seatRepository.findById(seatEntity.getId())).thenReturn(Optional.of(seatEntity));
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);

        ReservationResponseDto result = reservationService.createReservation(requestDto);

        assertNotNull(result.id());
        assertEquals("Deu a louca na chapeuzinho", result.movie());

        verify(userRepository, times(1)).findByEmail(any());
        verify(showtimeRepository, times(1)).findById(1);
        verify(seatRepository, times(1)).findById(184);
        verify(reservationRepository, times(1)).save(any(ReservationEntity.class));

    }


}