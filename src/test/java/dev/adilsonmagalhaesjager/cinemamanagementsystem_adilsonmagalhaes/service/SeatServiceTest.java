package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.SeatResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.SeatRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.interfaceJpa.ISeatProjection;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.mock.SeatDataFactory;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.mock.TestISeatProjectionDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatRepository repository;

    @InjectMocks
    private SeatService service;

    @Test
    @DisplayName("Should get all list and return true")
    void getAllSeatFromShowtime() {
        List<ISeatProjection>  listSeat = TestISeatProjectionDataFactory.seatProjectionList();
        when(repository.findSeatsByShowTimeWithStatus(1)).thenReturn(listSeat);


        List<SeatResponseDto> result = service.getAllSeatFromShowtime(1);

        assertEquals(3, result.size());
        assertEquals(2, result.get(1).id());
        assertEquals("AVAILABLE", result.get(0).status());
        assertEquals(20.0, result.get(2).value());

        verify(repository, times(1)).findSeatsByShowTimeWithStatus(1);

    }

    @Test
    @DisplayName("Should return true when find by id")
    void getSeatById() {

        SeatEntity seat = SeatDataFactory.seatAvailable();
        when(repository.findById(anyInt())).thenReturn(Optional.of(seat));

        SeatResponseDto responseDto = service.getSeatById(1);

        assertNotNull(responseDto);
        assertEquals(1, responseDto.id());
        assertEquals("A", responseDto.Room());
        assertEquals("VIP", responseDto.type());

        verify(repository, times(1)).findById(anyInt());
    }

    @Test
    void getSelectionSeats() {
        List<SeatEntity> listSeat = SeatDataFactory.setListSeatAllAvailable();

        when(repository.findAllById(anyList())).thenReturn(listSeat);

        List<SeatEntity> result = service.getSelectionSeats(List.of(1, 2, 3));

        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("VIP", result.get(0).getType().getName());
        assertEquals("A", result.get(0).getRoom().getName());
        assertEquals(2, result.get(1).getId());
        assertEquals("PREMIUM", result.get(1).getType().getName());
        assertEquals(3, result.get(2).getId());
        assertEquals(BigDecimal.valueOf(20), result.get(2).getType().getPrice());

    }


    @Test
    void ShouldThrowErrorSeatNotExist (){

        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.getSeatById(1));
        verify(repository, times(1)).findById(anyInt());

    }

    @Test
    void ShouldThrowErrorBecauseNotExistOneOfSeatOnTheList(){
        List<SeatEntity> listSeat = SeatDataFactory.setListSeatAllAvailable();
        when(repository.findAllById(anyList())).thenReturn(listSeat);
        assertThrows(NotFoundException.class, () -> service.getSelectionSeats(List.of(1, 2, 3, 4)));
        verify(repository, times(1)).findAllById(anyList());

    }


    /*
    * 1) Get All Seats Available      ✅
    * 2) Get selection seat   ✅
    * 4) Get All selection seats ✅
    *
    * 5) Exception:  Seat not exist ✅
    * 7) Exception: One of seat selected seat not exist ✅
    *
    * */
}