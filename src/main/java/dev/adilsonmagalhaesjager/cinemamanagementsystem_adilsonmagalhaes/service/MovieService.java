package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.MovieContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.MovieResponseDto;
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
        MovieEntity movie = repository.findById(id).orElseThrow(() -> NotFoundException.movieNotFound("" + id));
        return mappingMovieFromEntityToDto(movie);
    }

    @Override
    public MovieResponseDto getMovieByName(String name){
        MovieEntity movie = repository.findByTitle(name).orElseThrow(() -> NotFoundException.movieNotFound(name));
        return mappingMovieFromEntityToDto(movie);
    }

    @Override
    public List<MovieResponseDto> getAllMovies() {
        return repository.findAll().stream()
                .map(this::mappingMovieFromEntityToDto).toList();
}

    @Override
    public void verifyMovieExistById(int idMovie) {
        if(!repository.existsById(idMovie)){
            throw NotFoundException.movieNotFound("" + idMovie);
        }
    }

    private MovieResponseDto mappingMovieFromEntityToDto(MovieEntity e){
        return MovieResponseDto.builder().id(e.getId()).title(e.getTitle()).time(e.getTime()).build();
    }

}
