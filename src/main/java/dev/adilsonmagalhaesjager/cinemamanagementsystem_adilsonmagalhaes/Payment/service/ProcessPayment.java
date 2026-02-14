package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.Dto.CheckoutRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.adapter.PaymentGateway;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.core.PaymentContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.model.PaymentResponse;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationItemEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.ReservationStateManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
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

        Long value = 0L;

        for (ReservationItemEntity item : reservation.getItems()){
            BigDecimal price = item.getSeat().getType().getPrice();

            if (price != null) {
                long cents = price
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .longValueExact();

                value += cents;
        }}

        try{
            PaymentResponse paymentResponse = paymentGateway.process(
                    value,
                    request.paymentMethodId(),
                    request.userEmail()
            );

            if (!paymentResponse.status().equals("succeeded")){
                reservationStateManager.finalizePayment(reservation, paymentResponse);
                throw ReservationException.paymentDenied();
            }
            reservationStateManager.finalizePayment(reservation, paymentResponse);
        } catch (Exception e ){
            reservationStateManager.handlePaymentError(reservation);
            log.info(e.getMessage());
            throw ReservationException.internalErrorWhileProcessPayment();
        }

        return reservation;

    }
}
