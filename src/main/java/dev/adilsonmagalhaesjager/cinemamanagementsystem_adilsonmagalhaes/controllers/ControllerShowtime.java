package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ShowtimeContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.ShowtimeResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/showtimes")
public class ControllerShowtime {

    private final ShowtimeContract service;

    public ControllerShowtime(ShowtimeContract service) {
        this.service = service;
    }


    @RequestMapping("{id}")
    public ResponseEntity<List<ShowtimeResponseDto>> getShowTime(@Valid @PathVariable int id){
        return ResponseEntity.ok().body(service.getShowTimes(id));
    }

}
