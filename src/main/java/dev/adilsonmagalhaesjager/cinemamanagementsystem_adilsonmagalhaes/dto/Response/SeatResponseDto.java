package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.StatusSeat;

public record SeatResponseDto(
        int id,
        int seatRow,
        int seatColumn,
        StatusSeat StatusSeat
) {
}
