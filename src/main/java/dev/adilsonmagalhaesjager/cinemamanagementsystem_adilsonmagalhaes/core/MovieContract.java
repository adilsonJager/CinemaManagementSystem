package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.MovieResponseDto;

import java.util.List;

public interface MovieContract {

    MovieResponseDto getMovieById(int id);

    MovieResponseDto getMovieByName(String name);

    List<MovieResponseDto> getAllMovies();
    void verifyMovieExistById(int idMovie);
}
