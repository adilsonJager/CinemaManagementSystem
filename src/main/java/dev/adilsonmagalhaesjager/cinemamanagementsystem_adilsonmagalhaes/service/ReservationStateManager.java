package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Payment.model.PaymentResponse;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ConflictRunTimeException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.ReservationStatus;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ReservationStateManager {

    private final ReservationRepository repository;
    public ReservationStateManager(ReservationRepository reservationRepository) {
        this.repository = reservationRepository;
    }
    public ReservationEntity getAndLockForPayment(int id) {
        ReservationEntity reservation = repository.findById(id).orElseThrow(() -> NotFoundException.reservationNotExits(id));
        if (reservation.getStatus().equals(ReservationStatus.CONFIRMED)){
            throw ConflictRunTimeException.reservationAlreadyPaied();
        }
        reservation.setStatus(ReservationStatus.PROCESSING);
        return repository.saveAndFlush(reservation);

    }

    public void finalizePayment(ReservationEntity reservation, PaymentResponse paymentResponse){
        if (paymentResponse.status().equals("succeeded")){
            reservation.setStatus(ReservationStatus.CONFIRMED);
        } else {
            reservation.setStatus(ReservationStatus.PENDING);
        }
        repository.save(reservation);
    }

    public void handlePaymentError(ReservationEntity reservation){
        reservation.setStatus(ReservationStatus.PENDING);
        repository.save(reservation);
    }

}
