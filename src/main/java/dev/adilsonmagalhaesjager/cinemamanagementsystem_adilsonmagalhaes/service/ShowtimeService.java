package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ShowtimeContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.ShowtimeResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.MovieRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ShowtimeService implements ShowtimeContract {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }


    @Override
    public List<ShowtimeResponseDto> getShowTimes(int id_movie) {
        LocalDateTime now = LocalDateTime.now();

        if (!movieRepository.findById(id_movie).isPresent()){throw NotFoundException.movieNotFound("" + id_movie); }

        List<ShowtimeEntity> result =  showtimeRepository.findFutureShowtimes(now, id_movie);

        return  result.stream().map(
                p -> new ShowtimeResponseDto(
                        p.getId(),
                        p.getDateTime()
                )).toList();
    }
}
