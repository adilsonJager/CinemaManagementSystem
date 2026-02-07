package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.config.exception;


public class ConflictRunTimeException extends RuntimeException {

    public ConflictRunTimeException(String s) {super(s);}

    public static ConflictRunTimeException emailAlredyExist(String email){
        return new ConflictRunTimeException("Email" + (email) + "Already Exist! ");
    }


    public static ConflictRunTimeException seatAlreadyInUserBySameUser(){
        return new ConflictRunTimeException("You are have theses seats!" );
    }


    public static ConflictRunTimeException seatNotAvailable(){
        return new ConflictRunTimeException("Seat not available!");
    }

    public static ConflictRunTimeException seatNotEqualFromShowTime(){
        return new ConflictRunTimeException("The seat is from another room and section!");
    }


    public static ConflictRunTimeException reservationAlreadyPaied(){
        return new ConflictRunTimeException("Payment already done!");
    }







}
