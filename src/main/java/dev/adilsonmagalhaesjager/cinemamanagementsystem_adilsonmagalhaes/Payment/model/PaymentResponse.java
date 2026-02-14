package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.model;

import lombok.Builder;

@Builder
public record PaymentResponse(
        String id,
        String status,
        String last4,
        String email
) {
}
