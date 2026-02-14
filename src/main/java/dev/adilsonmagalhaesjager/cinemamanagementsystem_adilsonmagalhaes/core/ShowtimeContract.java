package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ShowtimeResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ShowtimeContract {


    List<ShowtimeResponseDto> getShowTimes(int idMovie, String date);

    ShowtimeResponseDto getShowtimeById(@NotNull Integer integer);

    ShowtimeEntity getShowtimeEntityById(int id);

    void existsById(int idShowtime);
}
