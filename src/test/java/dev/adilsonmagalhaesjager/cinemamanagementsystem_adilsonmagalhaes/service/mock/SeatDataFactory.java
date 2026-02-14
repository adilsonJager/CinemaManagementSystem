package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.mock;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.RoomEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatTypeEntity;

import java.math.BigDecimal;
import java.util.List;

public class SeatDataFactory {


    public static SeatEntity seatAvailable(){
        return SeatEntity.builder().id(1).seatColumn(1).seatRow(2).room(
                RoomEntity.builder().id(1).name("A").capacity(20).build())
                .type(
                        SeatTypeEntity.builder().id(1).name("VIP").price(BigDecimal.valueOf(20)).build()
                ).build();
    }

    public static List<SeatEntity> setListSeatAllAvailable(){
            SeatEntity s1 =  SeatEntity.builder().id(1).seatColumn(1).seatRow(2).room(
                        RoomEntity.builder().id(1).name("A").capacity(20).build())
                .type(
                        SeatTypeEntity.builder().id(1).name("VIP").price(BigDecimal.valueOf(20)).build()
                ).build();

        SeatEntity s2 =  SeatEntity.builder().id(2).seatColumn(2).seatRow(2).room(
                        RoomEntity.builder().id(1).name("A").capacity(20).build())
                .type(
                        SeatTypeEntity.builder().id(1).name("PREMIUM").price(BigDecimal.valueOf(20)).build()
                ).build();

        SeatEntity s3 =  SeatEntity.builder().id(3).seatColumn(3).seatRow(2).room(
                        RoomEntity.builder().id(1).name("A").capacity(20).build())
                .type(
                        SeatTypeEntity.builder().id(1).name("STANDARD").price(BigDecimal.valueOf(20)).build()
                ).build();


        return List.of(s1, s2, s3);
    }

}
