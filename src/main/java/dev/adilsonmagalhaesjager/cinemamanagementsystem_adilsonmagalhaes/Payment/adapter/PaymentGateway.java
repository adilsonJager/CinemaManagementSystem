package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.adapter;

public interface PaymentGateway {
    boolean process(Long amount, String methodId, String email);
}
