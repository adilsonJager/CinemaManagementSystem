package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;


import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.RoomResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.RoomName;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping("{name}")
    public ResponseEntity<RoomResponseDto> getRoomById(@Valid @PathVariable RoomName name){
        return ResponseEntity.ok().body(service.getRoom(name));
    }

}
