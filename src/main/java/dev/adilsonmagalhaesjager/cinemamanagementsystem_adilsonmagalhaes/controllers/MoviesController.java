package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.controllers;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.MovieResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/movie")
public class MoviesController {


    private final MovieService service;

    public MoviesController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAll(){
        return ResponseEntity.ok().body(this.service.getAllMovies());
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@Valid @PathVariable Integer id){
        return ResponseEntity.ok().body(this.service.getMovieById(id));
    }


}
