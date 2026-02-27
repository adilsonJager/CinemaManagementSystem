package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.Dto.CheckoutRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.adapter.PaymentGateway;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.core.PaymentContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.ReservationStateManager;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProcessPayment implements PaymentContract {

    private final PaymentGateway paymentGateway;
    private final ReservationStateManager reservationStateManager;

    public ProcessPayment(PaymentGateway paymentGateway, ReservationStateManager reservationStateManager) {
        this.paymentGateway = paymentGateway;
        this.reservationStateManager = reservationStateManager;
    }


    @Override
    public ReservationEntity execute(CheckoutRequestDto request) {

        ReservationEntity reservation = reservationStateManager.getAndLockForPayment(request.reservationId());

        try{

            long amount = reservation.getItems().stream()
                    .map(item -> item.getSeat().getType().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .multiply(BigDecimal.valueOf(100))
                    .longValue();

            boolean success = paymentGateway.process(
                    amount,
                    request.paymentMethodId(),
                    request.userEmail()
            );
            reservationStateManager.finalizePayment(reservation, success);

            if (!success){
                throw ReservationException.paymentDenied();
            }

        } catch (Exception e ){
            reservationStateManager.handlePaymentError(reservation);
            throw ReservationException.internalErrorWhileProcessPayment();
        }

        return reservation;

    }
}
