package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.UserRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.UserResponseDto;

public interface UsersContract {
    UserResponseDto getUserById(int id);
    UserResponseDto createUser(UserRequestDto dto);
    UserResponseDto getUserByEmail(String email);

}
