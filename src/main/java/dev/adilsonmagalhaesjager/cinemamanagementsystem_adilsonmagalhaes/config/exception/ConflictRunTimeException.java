package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictRunTimeException extends RuntimeException {

    public ConflictRunTimeException(String s) {super(s);}

    public static ConflictRunTimeException emailAlredyExist(String email){
        return new ConflictRunTimeException("Email" + (email) + "Already Exist! ");
    }

}
