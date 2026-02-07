package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.Dto.CheckoutRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestUpdateSeatDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseCancelationDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseDto;

public interface ReservationContract {

    ReservationResponseDto createReservation(ReservationRequestDto request);

    ReservationResponseDto updateSeat(ReservationRequestUpdateSeatDto request);

    ReservationResponseDto getReservation(int id);

    ReservationResponseCancelationDto cancelReservetion(int id);

    ReservationResponseDto payment(CheckoutRequestDto request);
}
