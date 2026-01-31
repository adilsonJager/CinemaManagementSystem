package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.SeatResponseDTO;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {

    private final SeatService service;

    public SeatController(SeatService seatService) {
        this.service = seatService;
    }

    @GetMapping("{id}")
    public ResponseEntity<List<SeatResponseDTO>> getAllSeatByShowtime(@PathVariable @Valid int id) {

        return ResponseEntity.ok().body(service.getAllSeatFromShowtime(id));

    }


}
