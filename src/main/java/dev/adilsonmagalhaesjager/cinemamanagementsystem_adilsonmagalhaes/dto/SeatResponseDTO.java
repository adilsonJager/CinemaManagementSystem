package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto;

public record SeatResponseDTO(
        int id,
        int seatRow,
        int seatColumn,
        StatusSeat StatusSeat
) {
}
