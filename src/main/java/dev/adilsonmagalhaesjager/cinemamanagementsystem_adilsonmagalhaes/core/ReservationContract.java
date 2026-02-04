package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseDto;

public interface ReservationContract {

    ReservationResponseDto createReservation(ReservationRequestDto request);

}
