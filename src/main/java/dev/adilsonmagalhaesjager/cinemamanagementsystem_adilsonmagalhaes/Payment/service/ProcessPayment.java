package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.adapter.PaymentGateway;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.core.PaymentContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ConflictRunTimeException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.Dto.CheckoutRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.ReservationStatus;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class ProcessPayment implements PaymentContract {

    private final PaymentGateway paymentGateway;
    private final ReservationRepository reservationRepository;

    public ProcessPayment(PaymentGateway paymentGateway, ReservationRepository reservationRepository) {
        this.paymentGateway = paymentGateway;
        this.reservationRepository = reservationRepository;
    }


    @Override
    public ReservationEntity execute(CheckoutRequestDto request) {
        ReservationEntity reservation = reservationRepository.findById(request.reservationId())
                .orElseThrow(() -> NotFoundException.reservationNotExits(request.reservationId()));

        if (reservation.getStatus().equals(ReservationStatus.CONFIRMED)){
            throw ConflictRunTimeException.reservationAlreadyPaied();
        }

        reservation.setStatus(ReservationStatus.PROCESSING);
        reservationRepository.saveAndFlush(reservation);

        try{
            Long amountInCents = 3000L;
            boolean success = paymentGateway.process(
                    amountInCents,
                    request.paymentMethodId(),
                    request.userEmail()
            );

            if (success){
                reservation.setStatus(ReservationStatus.CONFIRMED);

            } else  {
                reservation.setStatus(ReservationStatus.PENDING);
                reservationRepository.saveAndFlush(reservation);

                throw ReservationException.paymentDenied();
            }

            return reservationRepository.saveAndFlush(reservation);

        } catch (Exception e ){
            reservation.setStatus(ReservationStatus.PENDING);
            reservationRepository.save(reservation);
            throw ReservationException.internalErrorWhileProcessPayment();
        }


    }
}
