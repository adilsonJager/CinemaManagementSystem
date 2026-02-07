package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.mapper;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponseDto mappingShowtimeToDto(ReservationEntity reservation){
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .movie(reservation.getShowtime().getMovie().getTitle())
                .time(reservation.getShowtime().getDateTime().toString())
                .room(reservation.getShowtime().getRoom().getName())
                .row(reservation.getSeat().getSeatRow())
                .col(reservation.getSeat().getSeatColumn())
                .status(reservation.getStatus().getStatus())
                .build();
    }

}
