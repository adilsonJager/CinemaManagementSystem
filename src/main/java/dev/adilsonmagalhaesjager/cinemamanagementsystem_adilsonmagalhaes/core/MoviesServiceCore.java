package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.MovieResponseDto;

import java.util.List;

public interface MoviesServiceCore {

    MovieResponseDto getMovieById(int id);

    List<MovieResponseDto> getAllMovies(int id);

}
