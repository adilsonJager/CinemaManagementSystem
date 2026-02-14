package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.mock;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.interfaceJpa.ISeatProjection;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestISeatProjectionDataFactory {


    public static ISeatProjection seatProjection(
            int id,
            int row,
            int column,
            String status,
            String type,
            Double value
    ) {
        ISeatProjection projection = mock(ISeatProjection.class);

        when(projection.getId()).thenReturn(id);
        when(projection.getSeatRow()).thenReturn(row);
        when(projection.getSeatColumn()).thenReturn(column);
        when(projection.getStatus()).thenReturn(status);
        when(projection.getSeatType()).thenReturn(type);
        when(projection.getSeatValue()).thenReturn(value);

        return projection;
    }

    public static List<ISeatProjection> seatProjectionList() {
        return List.of(
                seatProjection(1, 1, 1, "AVAILABLE", "STANDARD", 10.0),
                seatProjection(2, 1, 2, "AVAILABLE", "STANDARD", 10.0),
                seatProjection(3, 1, 3, "AVAILABLE", "VIP", 20.0)
        );
    }


}
