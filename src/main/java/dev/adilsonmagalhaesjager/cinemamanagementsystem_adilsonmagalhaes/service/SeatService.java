package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.service;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.SeatContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.core.ShowtimeContract;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.dto.Response.SeatResponseDto;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.SeatEntity;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.SeatRepository;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.interfaceJpa.ISeatProjection;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SeatService implements SeatContract {

    private final SeatRepository seatRepository;
    private final ShowtimeContract serviceShowTime;



    public SeatService(SeatRepository seatRepository, ShowtimeContract serviceShowTime) {
        this.seatRepository = seatRepository;
        this.serviceShowTime = serviceShowTime;
    }


    @Override
    public List<SeatResponseDto> getAllSeatFromShowtime(int id_showtime) {

        serviceShowTime.existsById(id_showtime);

        List<ISeatProjection> seatFromDB = seatRepository.findSeatsByShowTimeWithStatus(id_showtime);
        return seatFromDB.stream().map(p -> SeatResponseDto.builder().id(p.getId()).seatRow(p.getSeatRow()).seatColumn(p.getSeatColumn()).status(p.getStatus()).type(p.getSeatType()).value(p.getSeatValue()).build()).toList();
    }

    @Override
    public SeatResponseDto getSeatById(int id){
        SeatEntity result = seatRepository.findById(id).orElseThrow( NotFoundException::seatNotFound);
        return SeatResponseDto.builder().id(result.getId()).Room(result.getRoom().getName()).seatRow(result.getSeatRow()).seatColumn(result.getSeatColumn()).type(result.getType().getName()).value(result.getType().getPrice().doubleValue()).build();
    }

    @Override
    public List<SeatEntity> getSelectionSeats(List<Integer> list){
        List<SeatEntity> result = seatRepository.findAllById(list);

        if (result.size() != list.size()){
            List<Integer> idsNaoEncontrados = list.stream()
                    .filter(id -> result.stream().noneMatch(seat -> seat.getId().equals(id)))
                    .toList();
            throw NotFoundException.seatNotFound( " " + idsNaoEncontrados);
        }

        return result;
    }


    @Override
    public Boolean seatAvailable(int showtimeId){
        return seatRepository.isSessionFull(showtimeId);
    }
}
