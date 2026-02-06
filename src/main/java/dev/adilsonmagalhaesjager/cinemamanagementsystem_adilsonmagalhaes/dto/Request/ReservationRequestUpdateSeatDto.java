package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request;

import jakarta.validation.constraints.NotNull;

public record ReservationRequestUpdateSeatDto(
        @NotNull int reservationID,
        @NotNull int seatID
) {
}
