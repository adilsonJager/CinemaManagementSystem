package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.UserResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.ReservationStatus;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ReservationRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.mock.ReservationDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationItemsService reservationItemsService;
    @Mock
    private UserService userService;
    @Mock
    private ShowtimeService showtimeService;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void createReservation() {

        ReservationRequestDto dto = new ReservationRequestDto("adilson@gmail.com", 1, List.of(1));
        ReservationEntity reservation = ReservationDataFactory.setReservationEntity();
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservation);
        when(userService.getUserByEmail(anyString())).thenReturn(new UserResponseDto(1, "adilson", "adilson@gmail.com"));
        when(showtimeService.getShowtimeEntityById(anyInt())).thenReturn(reservation.getShowtime());


        ReservationResponseDto result = reservationService.createReservation(dto);

        assertEquals(ReservationStatus.PENDING.getStatus(), result.status());


    }

    @Test
    void getReservation() {
    }

    @Test
    void payment() {
    }
}