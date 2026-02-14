package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByEmail(String email);
}
