package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.Dto.CheckoutRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;


public interface PaymentContract {
    ReservationEntity execute(CheckoutRequestDto request);
}
