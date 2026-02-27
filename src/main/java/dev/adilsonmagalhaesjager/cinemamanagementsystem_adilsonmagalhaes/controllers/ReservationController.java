package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;


import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ReservationContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Request.ReservationRequestDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseCancelationDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ReservationResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")

public class ReservationController {

    private final ReservationContract service;

    public ReservationController(ReservationContract service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createAReservation(@Valid @RequestBody ReservationRequestDto dto){
        return ResponseEntity.ok().body(service.createReservation(dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservation (@PathVariable int id){
            return ResponseEntity.ok().body(service.getReservation(id));
    }

//    @PutMapping("/cancel/{id}")
//    public ResponseEntity<ReservationResponseCancelationDto> cancelReservation(@PathVariable int id){
//
//        return ResponseEntity.ok().body(service.cancelReservetion(id));
//    }

}
