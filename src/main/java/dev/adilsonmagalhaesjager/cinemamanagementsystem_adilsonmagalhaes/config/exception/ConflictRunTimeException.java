package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception;


public class ConflictRunTimeException extends RuntimeException {

    public ConflictRunTimeException(String s) {super(s);}

    public static ConflictRunTimeException emailAlredyExist(String email){
        return new ConflictRunTimeException("Email" + (email) + "Already Exist! ");
    }

}
