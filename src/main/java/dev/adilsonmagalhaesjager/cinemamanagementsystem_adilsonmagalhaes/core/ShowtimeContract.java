package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ShowtimeResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ShowtimeContract {

    List<ShowtimeResponseDto> getShowTimes(@NotNull int id);

    ShowtimeResponseDto getShowtimeById(@NotNull Integer integer);

    ShowtimeEntity getShowtimeEntityById(int id);
}
