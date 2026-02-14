package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ReservationException extends RuntimeException{

    public ReservationException(String s){super(s);}

    public static ReservationException showtimeAlredyGone(){

        return new ReservationException("Showtime is old!");
    }

    public static ReservationException failToSaveOnDB (){
        return new ReservationException("Something wrong were while trying save register.");
    }

    public static ReservationException failToSaveOnDB (String msg){
        return new ReservationException(msg);
    }

    public static ReservationException internalErrorWhileProcessPayment (){
        return new ReservationException("Something wrong while payment");
    }

    public static ReservationException paymentDenied (){
        return new ReservationException("The payment was refused by the operator.");
    }

}
