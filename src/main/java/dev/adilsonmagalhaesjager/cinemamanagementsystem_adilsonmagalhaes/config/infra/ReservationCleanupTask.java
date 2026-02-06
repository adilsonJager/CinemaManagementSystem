package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.infra;



import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationCleanupTask {

    private final ReservationRepository reservationRepository;

    @Scheduled(fixedDelay = 60000)
    public void cleanPendingReservation(){
        int rowsAffect = reservationRepository.cancelExpiredReservation();
        if (rowsAffect > 0 ){
            log.info("Successfully ! {} reservations cancel", rowsAffect);
        }
    }

}
