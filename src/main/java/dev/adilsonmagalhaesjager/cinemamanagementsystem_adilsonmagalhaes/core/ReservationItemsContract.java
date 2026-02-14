package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ReservationItemEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;

import java.util.List;

public interface ReservationItemsContract {

    List<ReservationItemEntity> saveItems(List<Integer> seatIds, ReservationEntity reservation);
}
