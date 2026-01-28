package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model;

public enum RoomName {
    A("A"),
    B("B"),
    C("C"),
    D("D");

    private String room;

    RoomName(String room) {
        this.room = room;
    }


    public String getRoom(){
        return  this.room;
    }
}

