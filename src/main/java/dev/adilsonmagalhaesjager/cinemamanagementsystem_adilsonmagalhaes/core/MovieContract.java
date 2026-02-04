package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.MovieResponseDto;

import java.util.List;

public interface MovieContract {

    MovieResponseDto getMovieById(int id);

    List<MovieResponseDto> getAllMovies();

}
