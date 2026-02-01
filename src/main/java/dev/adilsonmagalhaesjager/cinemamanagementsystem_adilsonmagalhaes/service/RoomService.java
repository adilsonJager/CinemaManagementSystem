package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.RoomContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.RoomResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.RoomEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.RoomName;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements RoomContract {

    private final RoomRepository respository;


    public RoomService(RoomRepository respository) {
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
                    return new RoomResponseDto(
                            entity.getId(),
                            entity.getName(),
                            entity.getCapacity()
                    );
                }
        ).toList();

    }
}
