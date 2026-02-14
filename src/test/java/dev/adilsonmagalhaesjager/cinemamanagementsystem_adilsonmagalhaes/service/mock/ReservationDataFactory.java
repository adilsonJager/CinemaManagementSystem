package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.mock;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.*;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationDataFactory {

    public static ReservationEntity setReservationEntity(){
         ReservationEntity reservation = ReservationEntity.builder().id(1)
                .user(UserEntity.builder().id(1).name("adilson").email("adilson@gmail.com").build())
                .showtime(ShowtimeEntity.builder().id(1)
                                .movie(MovieEntity.builder().id(1).title("007").time(120).build())
                                .room(RoomEntity.builder().id(1).name("A").capacity(120).build())
                                .dateTime(LocalDateTime.now().plusDays(2))
                                .build()
                )
                .status(ReservationStatus.PENDING)
                .build();

         reservation.setItems(
                 List.of(
                         ReservationItemEntity.builder().id(1).reservation(reservation)
                                 .seat( SeatEntity.builder().id(1).seatColumn(1).seatRow(2).room(
                                                 RoomEntity.builder().id(1).name("A").capacity(20).build())
                                         .type(
                                                 SeatTypeEntity.builder().id(1).name("VIP").price(BigDecimal.valueOf(20)).build()
                                         ).build()
                         ).showtime(reservation.getShowtime()).build()
                 )
         );

         return reservation;
    }

}
