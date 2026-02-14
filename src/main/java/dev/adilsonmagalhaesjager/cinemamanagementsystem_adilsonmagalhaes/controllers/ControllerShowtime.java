package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ShowtimeContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ShowtimeResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/showtimes")

public class ControllerShowtime {

    private final ShowtimeContract service;

    public ControllerShowtime(ShowtimeContract service) {
        this.service = service;
    }

    @RequestMapping("{id}")
    public ResponseEntity<List<ShowtimeResponseDto>> getShowTime( @PathVariable int id, @RequestParam(name = "date") String dateStr){
        return ResponseEntity.ok().body(service.getShowTimes(id, dateStr));
    }

}
