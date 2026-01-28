package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.MovieNotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.MovieContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.MovieResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.MovieEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements MovieContract {

    private final MovieRepository repository;
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }


    @Override
    public MovieResponseDto getMovieById(int id) {
        MovieEntity movie = repository.findById(id).orElseThrow(() -> MovieNotFoundException.movieNotFound("error"));
        return new MovieResponseDto(movie.getId(), movie.getTitle(), movie.getTime());
    }

    @Override
    public List<MovieResponseDto> getAllMovies() {
        List<MovieResponseDto> movies = repository.findAll().stream().map(
                entity -> {
                    MovieResponseDto dto = new MovieResponseDto(
                            entity.getId(),
                            entity.getTitle(),
                            entity.getTime()
                    );
                    return dto;
                }
        ).toList();
        return movies;
    }
}
