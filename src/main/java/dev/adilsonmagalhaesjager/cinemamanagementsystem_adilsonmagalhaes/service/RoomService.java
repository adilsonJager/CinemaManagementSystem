package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.RoomContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.RoomResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.RoomEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.RoomName;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.RoomRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements RoomContract {

    private final RoomRespository respository;


    public RoomService(RoomRespository respository) {
        this.respository = respository;
    }

    @Override
    public RoomResponseDto getRoom(RoomName dto) {

        RoomEntity room = respository.findByName(dto.name()).orElseThrow(RuntimeException::new);

        return new RoomResponseDto(
                room.getId(),
                room.getName(),
                room.getCapacity()
        );

    }

    @Override
    public List<RoomResponseDto> getAllRoom() {

        return respository.findAll().stream().map(
                entity -> {
                    RoomResponseDto dto = new RoomResponseDto(
                            entity.getId(),
                            entity.getName(),
                            entity.getCapacity()
                    );
                    return dto;
                }
        ).toList();

    }
}
