package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, Integer> {

    @Query(value = """
        SELECT 
            st.id AS id,
            rm.name AS roomName,
            mv.title AS movieTitle,
            st.date_time AS dateTime,
            CASE 
                WHEN (
                    SELECT COUNT(ri.id)
                    FROM reservation_item ri
                    JOIN reservation r ON ri.reservation_id = r.id
                    WHERE ri.showtime_id = st.id
                    AND (
                        r.status IN ('CONFIRMED', 'PROCESSING')
                        OR (r.status = 'PENDING' AND r.created_at > NOW() - INTERVAL '4 minutes')
                    )
                ) >= rm.capacity THEN true 
                ELSE false 
            END AS isFull
        FROM showtime st
        JOIN room rm ON st.room_id = rm.id
        JOIN movie mv ON st.movie_id = mv.id
        WHERE st.movie_id = :movie
        AND st.date_time >= :now
        AND CAST(st.date_time AS date) = CAST(:selectedDate AS date)
        ORDER BY st.date_time ASC
        """, nativeQuery = true)
    List<IShowtimeWithStatusProjection> findAvailableShowtimesWithStatus(
            @Param("now") LocalDateTime now,
            @Param("selectedDate") LocalDateTime selectedDate,
            @Param("movie") Integer movie
    );

}

