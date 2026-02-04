package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response;
import lombok.Builder;

@Builder
public record ReservationResponseDto(
        int id,
        String movie,
        String time,
        String room,
        int row,
        int col,
        String status

) {
}
