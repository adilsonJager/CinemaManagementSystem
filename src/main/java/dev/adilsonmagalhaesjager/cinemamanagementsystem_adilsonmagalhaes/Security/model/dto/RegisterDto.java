package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.model.dto;


import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(
        @NotBlank String login,
        @NotBlank String password,
        @NotNull UserRole role
) {
}
