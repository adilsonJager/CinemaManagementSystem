package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.SeatResponseDTO;

import java.util.List;

public interface SeatContract {

    List<SeatResponseDTO> getAllSeatFromShowtime(int id);

}
