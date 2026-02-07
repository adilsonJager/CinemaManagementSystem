package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckoutRequestDto(
        @NotNull Integer reservationId,
        @NotBlank String paymentMethodId,
        @NotBlank String userEmail,
        @NotBlank String name
) {
}
