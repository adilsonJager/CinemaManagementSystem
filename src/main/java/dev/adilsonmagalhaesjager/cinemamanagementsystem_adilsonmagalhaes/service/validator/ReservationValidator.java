package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service.validator;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ConflictRunTimeException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.ShowtimeEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.SeatRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationValidator {

    public void validateShowtime(ShowtimeEntity showtime){
        if (showtime.getDateTime().plusMinutes(10).isBefore(LocalDateTime.now())){
            throw ReservationException.showtimeAlredyGone();
        }
    }

    public void validateSeatAvailability(SeatRepository seatRepository, Integer showtimeId, Integer seatId){
        if (!seatRepository.isSeatFree(showtimeId, seatId)) {
            throw ConflictRunTimeException.seatNotAvailable();
        }
    }

    public void validatorSeatChoiceEqualRoomShowTime(ShowtimeEntity showtime, SeatEntity seat){
        if (!showtime.getRoom().getId().equals(seat.getRoom().getId())){
            throw ConflictRunTimeException.seatNotEqualFromShowTime();
        }
    }
}
