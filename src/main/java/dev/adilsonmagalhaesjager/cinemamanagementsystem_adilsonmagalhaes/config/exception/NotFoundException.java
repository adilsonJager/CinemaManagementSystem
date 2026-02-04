package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends  RuntimeException{



    public NotFoundException(String s) {super(s);}

    public static NotFoundException movieNotFound (String movieId){
        return new NotFoundException("movie: "+ movieId +" not exists" );
    }

    public static NotFoundException seatNotFound(int row, int col){
        return new NotFoundException("Seat: Row-" + row + " Column: " + col + " Not exists");
    }

    public static NotFoundException seatNotFound(){
        return new NotFoundException("Seat Not exists");
    }

    public static NotFoundException showTimeNotExist(int s) {
        return new NotFoundException("ShowTime not exist! id: " + s);
    }

    public static NotFoundException userNotExists(int s) {
        return new NotFoundException("User not exist! id: " + s);
    }

    public static NotFoundException userEmailNotExists(String s) {
        return new NotFoundException("User not exist! Email: " + s);
    }

}
