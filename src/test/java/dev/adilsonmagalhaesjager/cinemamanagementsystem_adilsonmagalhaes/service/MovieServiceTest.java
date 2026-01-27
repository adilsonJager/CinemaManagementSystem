package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.MovieNotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.MovieResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.Movie;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
        Movie movieFake = new Movie(id, "Inception",154);
        when (repository.findById(id)).thenReturn(Optional.of(movieFake));

        //Action
         MovieResponseDto result = service.getMovieById(id);

        //Assert
        assertNotNull(result);
        assertEquals("Inception", result.title());
        assertEquals(154, result.time());
        verify(repository, times(1)).findById(id);
    }


    @Test
    @DisplayName("Should throw MovieNotFoundException when movie ID does not exist")
    void getRunTimeException(){
        int id = 99;
        when(repository.findById(id)).thenReturn(Optional.empty());

        //Action
        assertThrows(MovieNotFoundException.class, () -> service.getMovieById(id));

    }

    @Test
    void getAllMovies() {
    }
}