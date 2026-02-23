package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.SeatResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatEntity;

import java.util.List;

public interface SeatContract {

    List<SeatResponseDto> getAllSeatFromShowtime(int id);

    SeatResponseDto getSeatById(int id);

    List<SeatEntity> getSelectionSeats(List<Integer> list);

    Boolean seatAvailable(int showtimeId);
}
