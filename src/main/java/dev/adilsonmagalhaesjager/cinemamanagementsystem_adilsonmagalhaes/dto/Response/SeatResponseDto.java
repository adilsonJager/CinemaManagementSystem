package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response;


import lombok.Builder;


@Builder
public record SeatResponseDto(
        int id,
        int seatRow,
        int seatColumn,
        String Room,
        String status,
        String type,
        Double value
) {
}
