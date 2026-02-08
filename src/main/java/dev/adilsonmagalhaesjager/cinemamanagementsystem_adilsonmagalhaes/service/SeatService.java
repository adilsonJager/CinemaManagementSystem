package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.SeatContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.interfaceJpa.ISeatProjection;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.SeatResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.StatusSeat;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.SeatRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ShowtimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SeatService implements SeatContract {

    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;



    public SeatService(SeatRepository seatRepository, ShowtimeRepository showtimeRepository) {
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
    }


    @Override
    public List<SeatResponseDto> getAllSeatFromShowtime(int id_showtime) {

        if (!showtimeRepository.existsById(id_showtime)){
            throw NotFoundException.showTimeNotExist( id_showtime);
        }
        List<ISeatProjection> seatFromDB = seatRepository.findSeatsByShowTimeWithStatus(id_showtime);
        return seatFromDB.stream().map(p -> new SeatResponseDto(p.getId(), p.getSeatRow(), p.getSeatColumn(), StatusSeat.valueOf(p.getStatus()))).toList();
    }


    @Override
    public List<SeatEntity> getSelectionSeats(List<Integer> list){
        List<SeatEntity> result = seatRepository.findAllById(list);
        if (result.size() != list.size()){
            throw NotFoundException.seatNotFound();
        }

        return result;
    }

}
