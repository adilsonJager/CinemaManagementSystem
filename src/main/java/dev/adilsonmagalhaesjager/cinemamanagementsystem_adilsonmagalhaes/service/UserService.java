package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ConflictRunTimeException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.UsersContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.UserRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.UserResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.UserEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UsersContract {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserResponseDto getUserById(int id) {

        UserEntity user = repository.findById(id).orElseThrow(() -> NotFoundException.userNotExists(id));
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());

    }

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {

        if (repository.findByEmail(dto.email()).isPresent()){
            throw ConflictRunTimeException.emailAlredyExist(dto.email());
        }

        UserEntity user = new UserEntity();
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setPassword(dto.password());
        UserEntity saveUser = repository.save(user);
        return new UserResponseDto(saveUser.getId(), saveUser.getName(), saveUser.getEmail());


    }
}
