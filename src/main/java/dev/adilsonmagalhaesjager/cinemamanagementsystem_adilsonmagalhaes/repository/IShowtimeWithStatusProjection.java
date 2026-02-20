package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository;

import java.time.LocalDateTime;

public interface IShowtimeWithStatusProjection {
    Integer getId();
    String getRoomName();
    String getMovieTitle();
    LocalDateTime getDateTime();
    Boolean getIsFull();
}
