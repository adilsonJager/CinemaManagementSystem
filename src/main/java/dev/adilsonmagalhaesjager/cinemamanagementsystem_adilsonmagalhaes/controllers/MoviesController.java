package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.MovieContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.MovieResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:8081")
public class MoviesController {


    private final MovieContract service;

    public MoviesController(MovieContract service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAll(){
        return ResponseEntity.ok().body(this.service.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@Valid @PathVariable Integer id){
        return ResponseEntity.ok().body(this.service.getMovieById(id));
    }


}
