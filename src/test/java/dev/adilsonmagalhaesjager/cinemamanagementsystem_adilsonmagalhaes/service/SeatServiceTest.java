package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.interfaceJpa.ISeatProjection;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.SeatResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.StatusSeat;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.SeatRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ShowtimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SeatServiceTest {


    @Mock
    private SeatRepository repository;

    @Mock
    ShowtimeRepository showtimeRepository;

    @InjectMocks
    private SeatService service;

    @Test
    void getAllSeatFromShowtime() {

        ISeatProjection s1 = new SeatProjectionTestImpl(1, 1, 1, StatusSeat.Available.name());
        ISeatProjection s2 = new SeatProjectionTestImpl(2, 1, 2, StatusSeat.Not_Available.name());
        ISeatProjection s3 = new SeatProjectionTestImpl(3, 1, 3, StatusSeat.Available.name());
        when (repository.findSeatsByShowTimeWithStatus(1)).thenReturn(List.of(s1, s2, s3));


        List<SeatResponseDto> result = this.service.getAllSeatFromShowtime(1);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.get(0).seatRow()),
                () -> assertEquals(1, result.get(0).seatColumn()),
                () -> assertEquals(2, result.get(1).seatColumn()),
                () -> assertEquals(StatusSeat.Available, result.get(2).StatusSeat())
        );

        verify(repository, times(1)).findSeatsByShowTimeWithStatus(1);


    }

    @Test
    @DisplayName("Should throw RunTimeException when showtime ID does not exist")
    void getRunTimeException(){
        when(showtimeRepository.existsById(999)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> service.getAllSeatFromShowtime(999));
    }



    private static class SeatProjectionTestImpl implements ISeatProjection {
        private int id;
        private int seatRow;
        private int seatColumn;
        private String status;

        public SeatProjectionTestImpl(int id, int seatRow, int seatColumn, String status) {
            this.id = id;
            this.seatRow = seatRow;
            this.seatColumn = seatColumn;
            this.status = status;
        }

        @Override public int getId() { return id; }
        @Override public int getSeatRow() { return seatRow; }
        @Override public int getSeatColumn() { return seatColumn; }
        @Override public String getStatus() { return status; }
    }


}