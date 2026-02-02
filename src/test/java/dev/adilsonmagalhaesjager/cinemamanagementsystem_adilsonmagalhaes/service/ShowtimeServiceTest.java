package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.MovieEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.RoomEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.MovieRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ShowtimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ShowtimeServiceTest {

    @Mock
    private ShowtimeRepository repository;
    @Mock
    private MovieRepository movieRepository;


    @InjectMocks
    private ShowtimeService service;


    private ShowtimeEntity s1;
    private ShowtimeEntity s2;


    @BeforeEach
    void setUp(){
        s1 = new ShowtimeEntity(
                1,
                new MovieEntity(1, "Test1", 120),
                new RoomEntity(1, "A", 123),
                LocalDateTime.now().plusMonths(1));

        s2 = new ShowtimeEntity(
                2,
                new MovieEntity(1, "Test1", 120),
                new RoomEntity(2, "A", 123),
                LocalDateTime.now().plusMonths(3));
    }

    @Test
    void getShowTimes() {

        int movieId = s1.getMovie().getId();

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(s1.getMovie()));
        when(repository.findFutureShowtimes(any(LocalDateTime.class), eq(movieId))).thenReturn(List.of(s1, s2));

        var result = service.getShowTimes(movieId);

        assertEquals(1, result.get(0).id());
        verify(repository, times(1)).findFutureShowtimes(any(LocalDateTime.class), eq(movieId));


        //WHEN USER ANY ALL METHODS NEED USER EQ

    }


    @Test
    @DisplayName(" Should Get Not Found Movie")
    void ShoudlRetunrNotFound(){
        int id = 122;
        when(movieRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getShowTimes(id));


    }
}