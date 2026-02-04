package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;


import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ReservationContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationContract service;

    public ReservationController(ReservationContract service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createAReservation(@Valid @RequestBody ReservationRequestDto dto){

        return ResponseEntity.ok().body(service.createReservation(dto));

    }

}
