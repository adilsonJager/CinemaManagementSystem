package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank String name,
        @NotBlank String email
) {
}
