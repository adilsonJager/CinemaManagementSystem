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

    @Query("SELECT s FROM showtime s WHERE movie.id = :movie AND s.dateTime > :now ORDER BY s.dateTime ASC")
    List<ShowtimeEntity> findFutureShowtimes (@Param("now")LocalDateTime now, @Param("movie")Integer id);

}

