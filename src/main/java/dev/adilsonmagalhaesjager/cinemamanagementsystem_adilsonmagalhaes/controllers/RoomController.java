package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;


import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.RoomContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.RoomResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.RoomName;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomContract service;

    public RoomController(RoomContract service) {
        this.service = service;
    }

    @GetMapping("{name}")
    public ResponseEntity<RoomResponseDto> getRoomById(@Valid @PathVariable RoomName name){
        return ResponseEntity.ok().body(service.getRoom(name));
    }

}
