package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.SeatContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.ISeatProjection;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.SeatResponseDTO;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.StatusSeat;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.SeatRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.ShowtimeRespository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SeatService implements SeatContract {

    private final SeatRepository seatRepository;
    private final ShowtimeRespository showtimeRespository;

    public SeatService(SeatRepository seatRepository, ShowtimeRespository showtimeRespository) {
        this.seatRepository = seatRepository;
        this.showtimeRespository = showtimeRespository;
    }


    @Override
    public List<SeatResponseDTO> getAllSeatFromShowtime(int id_showtime) {

        if (!showtimeRespository.existsById(id_showtime)){
            throw new RuntimeException("Sesson not exist! " + id_showtime);
        }

        List<ISeatProjection> seatFromDB = seatRepository.findSeatsByShowTimeWithStatus(id_showtime);

        return seatFromDB.stream().map(p -> new SeatResponseDTO(p.getId(), p.getSeatRow(), p.getSeatColumn(), StatusSeat.valueOf(p.getStatus()))).toList();
    }
}
