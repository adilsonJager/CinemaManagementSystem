package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums;

public enum StatusSeat {
    Available("Available"),
    Not_Available("Not Available");

    private String status;

    StatusSeat(String status) {this.status = status;}

    public String getStatus() {return this.status;}
}
