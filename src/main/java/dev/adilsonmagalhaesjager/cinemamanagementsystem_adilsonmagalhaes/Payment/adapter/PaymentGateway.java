package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.adapter;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.model.PaymentResponse;

public interface PaymentGateway {
    PaymentResponse process(Long amount, String methodId, String email);
}
