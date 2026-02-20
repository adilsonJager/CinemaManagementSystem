package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.Security.repository.UserInternalRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@org.springframework.stereotype.Service
public class Service implements UserDetailsService {

    private final UserInternalRepository repository;

    public Service(UserInternalRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }


}
