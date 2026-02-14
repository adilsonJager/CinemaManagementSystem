package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response;
import lombok.Builder;


import java.util.List;

@Builder
public record ReservationResponseDto(
        int id,
        String movie,
        String time,
        String room,
        List<String> seats,
        String status

) {
}
