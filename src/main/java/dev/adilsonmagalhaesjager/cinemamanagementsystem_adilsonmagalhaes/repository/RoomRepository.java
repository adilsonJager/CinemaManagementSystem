package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Integer>{

    Optional<RoomEntity> findByName(String name);

}
