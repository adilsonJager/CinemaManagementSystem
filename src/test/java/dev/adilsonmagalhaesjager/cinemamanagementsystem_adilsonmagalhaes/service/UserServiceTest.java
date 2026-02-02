package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ConflictRunTimeException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.UserRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.UserResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.UserEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    UserEntity user1;
    UserRequestDto userReqDto;

    @BeforeEach
    void setUp(){
        user1 = new UserEntity(1, "adilson", "teste1@gmail.com", "12345");
        userReqDto = new UserRequestDto( "adilson", "teste1@gmail.com", "12345");
    }

    @Test
    @DisplayName("Should return a UserResponseDto when searching by a valid email")
    void getUserByEmail() {
        when (repository.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));
        UserResponseDto result = service.getUserByEmail(user1.getEmail());
        assertNotNull(result);
        assertEquals(1, result.id());
        verify(repository, times(1)).findByEmail(user1.getEmail());

    }

    @ParameterizedTest
    @ValueSource(strings = {"id", "email"})
    @DisplayName("Should throw NotFoundException when user is not found by ID or Email")
    void getRunTimeException(String identify){
        if (identify.contains("email")){
            when(repository.findByEmail(user1.getEmail())).thenReturn(Optional.empty());
        } else{
            when(repository.findById(user1.getId())).thenReturn(Optional.empty());
        }
        assertThrows(NotFoundException.class, () -> {
            if (identify.contains("email")){
                service.getUserByEmail(user1.getEmail());
            }else {
                service.getUserById(user1.getId());
            }
        });
        if (identify.contains("email")){
            verify(repository).findByEmail(user1.getEmail());
        } else {
            verify(repository).findById(user1.getId());
        }

    }


    @Test
    @DisplayName("Should successfully create a new user and return UserResponseDto")
    void createUser() {
        when(repository.findByEmail(userReqDto.email())).thenReturn(Optional.empty());
        when(repository.save(any(UserEntity.class))).thenReturn(user1);
        UserResponseDto result = service.createUser(userReqDto);
        assertNotNull(result);
        assertEquals(user1.getId(), result.id());
        verify(repository, times(1)).save(any(UserEntity.class));

    }

    @Test
    @DisplayName("Should throw ConflictRunTimeException when attempting to create a user with an existing email")
    void createUserConflict(){
        when(repository.findByEmail(userReqDto.email())).thenReturn(Optional.of(user1));
        assertThrows(ConflictRunTimeException.class, () -> service.createUser(userReqDto));
        verify(repository, never()).save(any());
    }

}