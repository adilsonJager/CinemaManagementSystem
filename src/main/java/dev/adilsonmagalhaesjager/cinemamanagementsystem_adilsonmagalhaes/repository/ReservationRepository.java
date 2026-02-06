package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM reservation
                WHERE status = 'PENDING'
                        AND created_at < NOW() - INTERVAL '4 minutes'
            """, nativeQuery = true)
    int cancelExpiredReservation();

}
