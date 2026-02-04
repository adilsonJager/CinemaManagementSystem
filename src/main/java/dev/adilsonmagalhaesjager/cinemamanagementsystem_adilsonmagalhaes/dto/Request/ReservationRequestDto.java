package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request;

import jakarta.validation.constraints.NotNull;

public record ReservationRequestDto(
        String email_user,
        @NotNull Integer id_showtime,
        @NotNull Integer id_seat
){
}
