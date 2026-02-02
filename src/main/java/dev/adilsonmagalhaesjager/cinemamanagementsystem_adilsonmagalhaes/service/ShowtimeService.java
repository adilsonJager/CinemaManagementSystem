package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ShowtimeContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.ShowtimeResponseDto;
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
    public List<ShowtimeResponseDto> getShowTimes(int idMovie) {
        LocalDateTime now = LocalDateTime.now();

        if (movieRepository.findById(idMovie).isEmpty()){throw NotFoundException.movieNotFound("" + idMovie); }

        return  showtimeRepository.findFutureShowtimes(now, idMovie).stream().map(
                p -> new ShowtimeResponseDto(
                        p.getId(),
                        p.getDateTime()
                )).toList();
/*
        List<ShowtimeEntity> result =  showtimeRepository.findFutureShowtimes(now, id_movie);
        return  result.stream().map(
                p -> new ShowtimeResponseDto(
                        p.getId(),
                        p.getDateTime()
                )).toList();
*/
    }
}
