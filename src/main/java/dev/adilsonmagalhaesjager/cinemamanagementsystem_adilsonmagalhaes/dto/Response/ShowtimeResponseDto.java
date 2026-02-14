package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ShowtimeResponseDto(
    int id,
    String room,
    String movie,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime dateTime
) {
}
