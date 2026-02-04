package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.interfaceJpa.ISeatProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Integer> {

    @Query(value = """
            SELECT
                seat.id AS id,
                seat.seat_row AS "seatRow",
                seat.seat_column AS "seatColumn",
                CASE
                    WHEN reservation.id IS NOT NULL THEN 'Not_Available'
                    ELSE 'Available'
                    END AS status
                       FROM seat seat
                JOIN showtime showTime ON seat.room_id = showTime.room_id
                LEFT JOIN reservation reservation ON seat.id = reservation.seat_id 
                AND reservation.showtime_id = showTime.id
                AND reservation.status IN ('PENDING', 'CONFIRMED')
                WHERE showTime.id = :showtimeId
            """, nativeQuery = true)
    List<ISeatProjection> findSeatsByShowTimeWithStatus(@Param("showtimeId") int showtimeId);
}
