package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;


import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.UsersContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.UserRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UsersContract service;


    public UserController(UsersContract service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id){
        return ResponseEntity.ok().body(service.getUserById(id));
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto dto){

        return ResponseEntity.ok().body(service.createUser(dto));

    }
}
