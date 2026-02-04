package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception;

public class ReservationException extends RuntimeException{

    public ReservationException(String s){super(s);}

    public static ReservationException showtimeAlredyGone(String showtime){
        return new ReservationException("Showtime is old!");
    }

    public static ReservationException failToSaveOnDB (String msg){
        return new ReservationException("Something wrong were while trying save register.");
    }

}
