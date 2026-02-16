package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.model.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(
        @NotBlank String login, @NotBlank String password
) {
}
