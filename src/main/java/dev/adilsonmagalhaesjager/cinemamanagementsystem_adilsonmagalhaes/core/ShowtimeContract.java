package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ShowtimeResponseDto;

import java.util.List;

public interface ShowtimeContract {

    List<ShowtimeResponseDto> getShowTimes(int id);

}
