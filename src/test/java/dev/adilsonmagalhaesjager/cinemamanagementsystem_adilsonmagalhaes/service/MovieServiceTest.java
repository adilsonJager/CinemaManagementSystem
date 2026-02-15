package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.MovieResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.MovieEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {


    @Mock
    private MovieRepository repository;

    @InjectMocks
    private MovieService service;

    @Test
    @DisplayName("Should return a MovieResponseDto when a valid ID is provided")
    void ShouldBeGetMovieWhenIdExists() {
        //Arrange
        int id = 1;
        MovieEntity movieFake = new MovieEntity(id, "Inception",154, "Filme muito bom", "terror", 18, "sem url");
        when (repository.findById(id)).thenReturn(Optional.of(movieFake));

        //Action
         MovieResponseDto result = service.getMovieById(id);

        //Assert
        assertNotNull(result);
        assertEquals("Inception", result.title());
        assertEquals(154, result.duration());
        verify(repository, times(1)).findById(id);
    }


    @Test
    @DisplayName("Should throw NotFoundException when movie ID does not exist")
    void getRunTimeException(){
        int id = 99;
        when(repository.findById(id)).thenReturn(Optional.empty());

        //Action
        assertThrows(NotFoundException.class, () -> service.getMovieById(id));

    }

    @Test
    @DisplayName("Should return a list of movies")
    void getAllMovies() {
        MovieEntity m1 = new MovieEntity(1, "movie 1", 110, "Filme muito bom", "terror", 18, "sem url");
        MovieEntity m2 = new MovieEntity(2, "movie 2", 120, "Filme muito bom", "terror", 18, "sem url");
        MovieEntity m3 = new MovieEntity(3, "movie 3", 130, "Filme muito bom", "terror", 18, "sem url");
        when(repository.findAll()).thenReturn(List.of(m1, m2, m3));
        List<MovieResponseDto> result = service.getAllMovies();



        assertAll(
                () ->  assertEquals(3, result.size()),
                () -> assertEquals("movie 3", result.get(2).title()),
                () ->  assertEquals(120, result.get(1).duration())
        );
        verify(repository, times(1)).findAll();
    }
}