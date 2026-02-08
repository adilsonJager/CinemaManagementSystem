package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.MovieContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ShowtimeContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.ShowtimeResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ShowtimeService implements ShowtimeContract {

    private final ShowtimeRepository showtimeRepository;
    private final MovieContract movieService;

    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieContract movieService) {
        this.showtimeRepository = showtimeRepository;
        this.movieService = movieService;
    }

    @Override
    public List<ShowtimeResponseDto> getShowTimes(int idMovie) {
        LocalDateTime now = LocalDateTime.now();
        movieService.verifyMovieExistById(idMovie);
        return  showtimeRepository.findFutureShowtimes(now, idMovie).stream().map(
                this::mappingEntityToDto
                ).toList();
    }

    @Override
    public ShowtimeResponseDto getShowtimeById(Integer id) {
        ShowtimeEntity showtime = showtimeRepository.findById(id).orElseThrow(() ->NotFoundException.showTimeNotExist(id));
        return mappingEntityToDto(showtime);
    }

    @Override
    public ShowtimeEntity getShowtimeEntityById(int id){
        return showtimeRepository.findById(id).orElseThrow(() ->NotFoundException.showTimeNotExist(id));

    }

    private ShowtimeResponseDto mappingEntityToDto(ShowtimeEntity entity){
        return ShowtimeResponseDto.builder().id(entity.getId()).room(entity.getRoom().getName()).movie(entity.getMovie().getTitle()).dateTime(entity.getDateTime()).build();
    }

}
