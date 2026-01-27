package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends  RuntimeException{



    public MovieNotFoundException(String s) {super(s);}

    public static MovieNotFoundException movieNotFound (String movieId){
        return new MovieNotFoundException("movie: "+ movieId +" movie not exists" );
    }

}
