package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.repository;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.model.UserInternal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserInternalRepository extends JpaRepository<UserInternal, Integer> {

    UserDetails findByLogin(String login);

}
