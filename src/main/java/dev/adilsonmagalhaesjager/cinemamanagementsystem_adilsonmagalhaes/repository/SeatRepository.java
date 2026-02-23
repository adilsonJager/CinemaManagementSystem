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
                stype.name AS "seatType",
                stype.price AS "seatValue",
                CASE
                    WHEN r.id IS NOT NULL THEN 'Not_Available'
                    ELSE 'Available'
                    END AS status
                FROM seat seat
                JOIN seattype stype ON seat.type_id = stype.id
                JOIN showtime st ON seat.room_id = st.room_id
                LEFT JOIN reservation_item ri ON seat.id = ri.seat_id AND ri.showtime_id = st.id
                LEFT JOIN reservation r ON ri.reservation_id = r.id
                AND (
                    r.status = 'CONFIRMED'
                    OR (r.status = 'PENDING' OR r.status = 'PROCESSING' AND r.created_at >  NOW() - INTERVAL '4 minutes' )
                    )
                WHERE st.id = :showtimeId
            """, nativeQuery = true)
    List<ISeatProjection> findSeatsByShowTimeWithStatus(@Param("showtimeId") int showtimeId);


    @Query(value = """
    SELECT CASE WHEN COUNT(r.id) > 0 THEN false ELSE true END
    FROM reservation_item ri
    JOIN reservation r ON ri.reservation_id = r.id
    WHERE ri.showtime_id = :showtimeId
        AND ri.seat_id = :seatId
        AND (r.status IN ('CONFIRMED', 'PROCESSING')
                OR
                (r.status = 'PENDING')
                AND r.created_at > NOW() - INTERVAL '4 minutes'
                )
    """, nativeQuery = true)
    boolean isSeatFree(@Param("showtimeId") int showtimeId, @Param("seatId") int seatId);

    @Query(value = """
    SELECT CASE 
        WHEN COUNT(ri.id) >= s.room_id THEN true 
        ELSE false 
    END
    FROM reservation_item ri
    JOIN reservation r ON ri.reservation_id = r.id
    JOIN showtime s ON ri.showtime_id = s.id
    JOIN room ro ON s.room_id = ro.id
    WHERE ri.showtime_id = :showtimeId
        AND (r.status IN ('CONFIRMED', 'PROCESSING')
            OR (r.status = 'PENDING' AND r.created_at > NOW() - INTERVAL '4 minutes'))
    GROUP BY ro.capacity
    """, nativeQuery = true)
    boolean isSessionFull(@Param("showtimeId") int showtimeId);

}


