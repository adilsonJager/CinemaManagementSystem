package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ConflictRunTimeException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.NotFoundException;
import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception.ReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFound(NotFoundException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(ConflictRunTimeException.class)
    private ResponseEntity<RestErrorMessage> conflictRuntimeException(ConflictRunTimeException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(ReservationException.class)
    private ResponseEntity<RestErrorMessage> conflictRuntimeException(ReservationException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }


    @ExceptionHandler(Exception.class)
    private ResponseEntity<RestErrorMessage> handleEverythingElse(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

}
