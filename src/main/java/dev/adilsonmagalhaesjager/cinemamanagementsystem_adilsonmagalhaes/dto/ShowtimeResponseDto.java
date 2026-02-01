package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ShowtimeResponseDto(
    int id,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime dateTime
) {
}
