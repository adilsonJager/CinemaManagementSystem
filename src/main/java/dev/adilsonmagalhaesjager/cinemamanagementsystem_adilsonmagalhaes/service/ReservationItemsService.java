package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ConflictRunTimeException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ReservationItemsContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.SeatContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationItemEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ReservationItemRespository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ReservationItemsService implements ReservationItemsContract {

    private final ReservationItemRespository repository;
    private final SeatContract seatservice;

    public ReservationItemsService(ReservationItemRespository repository, SeatContract seatservice) {
        this.repository = repository;
        this.seatservice = seatservice;
    }


    @Override
    public List<ReservationItemEntity> saveItems(List<Integer> seatIds, ReservationEntity reservation) {

        List<SeatEntity> seat = seatservice.getSelectionSeats(seatIds);


        for (SeatEntity s : seat){
            if (!s.getRoom().getId().equals(reservation.getShowtime().getRoom().getId()) ){
                throw ConflictRunTimeException.seatNotEqualFromShowTime();
            }
        }


        List<ReservationItemEntity> items = seat.stream()
                        .map(s -> ReservationItemEntity.builder()
                                .id(null)
                                .reservation(reservation)
                                .seat(s)
                                .showtime(reservation.getShowtime())
                                .build())
                .collect(Collectors.toList());

        repository.saveAll(items);

        return items;

    }

}
