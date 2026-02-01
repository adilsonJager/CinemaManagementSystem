package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.SeatContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.SeatResponseDto;
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

    private final SeatContract service;

    public SeatController(SeatContract seatService) {
        this.service = seatService;
    }

    @GetMapping("{id}")
    public ResponseEntity<List<SeatResponseDto>> getAllSeatByShowtime(@PathVariable @Valid int id) {

        return ResponseEntity.ok().body(service.getAllSeatFromShowtime(id));

    }


}
