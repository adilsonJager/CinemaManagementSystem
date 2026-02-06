package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationResponseCancelationDto(
        int id,
        String status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dateTime
) {
}
