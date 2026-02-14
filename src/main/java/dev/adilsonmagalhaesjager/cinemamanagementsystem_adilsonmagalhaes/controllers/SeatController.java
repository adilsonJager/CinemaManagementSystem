package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.SeatContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.SeatResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")

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
