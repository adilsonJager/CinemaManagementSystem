package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.SeatResponseDto;

import java.util.List;

public interface SeatContract {

    List<SeatResponseDto> getAllSeatFromShowtime(int id);
}
