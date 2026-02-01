package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.RoomResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.RoomName;

import java.util.List;

public interface RoomContract {

    RoomResponseDto getRoom(RoomName name);
    List<RoomResponseDto> getAllRoom();
}
