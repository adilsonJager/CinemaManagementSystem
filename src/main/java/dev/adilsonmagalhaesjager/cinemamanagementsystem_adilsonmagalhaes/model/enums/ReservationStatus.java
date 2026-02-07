package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums;

public enum ReservationStatus {

    PENDING ("PAYMENT PENDING"),
    CONFIRMED ("PAYMENT CONFIRMED"),
    PROCESSING("PROCESSING PAYMENT");

    private String status;

      ReservationStatus(String status){
        this.status = status;
    }

    public String getStatus(){
          return this.status;
    }


}
