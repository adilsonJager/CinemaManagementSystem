package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;


import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.MovieNotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.MoviesServiceCore;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.MovieResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.Movie;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements MoviesServiceCore {

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }


    @Override
    public MovieResponseDto getMovieById(int id) {

        Movie movie = repository.findById(id).orElseThrow(() -> MovieNotFoundException.movieNotFound("error"));

        return new MovieResponseDto(movie.getId(), movie.getTitle(), movie.getTime());

    }

    @Override
    public List<MovieResponseDto> getAllMovies(int id) {
        return null;
    }
}
