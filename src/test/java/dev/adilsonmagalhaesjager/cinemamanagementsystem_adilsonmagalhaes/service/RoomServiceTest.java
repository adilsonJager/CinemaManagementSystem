package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.RoomResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.RoomEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.RoomName;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.RoomRespository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRespository repository;

    @InjectMocks
    private RoomService service;

    @Test
    void ShouldBeGetRoomByNameExists() {


        RoomEntity roomFake = new RoomEntity(1, "A", 235);
        when (repository.findByName("A")).thenReturn(Optional.of(roomFake));

        RoomResponseDto result = service.getRoom(RoomName.A);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("A", result.name()),
                () -> assertEquals(1, result.id()),
                () -> assertEquals(235, result.capacity())
        );
        verify(repository, times(1)).findByName("A");

    }

    @Test
    @DisplayName("Should get RunTimeException if room not exist")
    void getRunTimeExceptionRoomNotExists(){
        when(repository.findByName(RoomName.A.name())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getRoom(RoomName.A));
    }

    @Test
    @DisplayName("Should get a list of rooms")
    void getAllRoom() {

        RoomEntity r1 = new RoomEntity(1, "A", 100);
        RoomEntity r2 = new RoomEntity(2, "B", 200);
        RoomEntity r3 = new RoomEntity(3, "C", 300);
        when(repository.findAll()).thenReturn(List.of(r1, r2, r3));

        List<RoomResponseDto> result = service.getAllRoom();

        assertAll(
                () -> assertEquals(1, result.get(0).id()),
                () -> assertEquals("B", result.get(1).name()),
                () -> assertEquals(300, result.get(2).capacity()),
                () -> assertEquals(3, result.size())
        );

        verify(repository, times(1)).findAll();
    }
}