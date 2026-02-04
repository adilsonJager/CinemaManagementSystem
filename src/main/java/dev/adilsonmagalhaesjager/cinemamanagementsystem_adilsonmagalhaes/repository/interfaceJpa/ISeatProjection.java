package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.repository.interfaceJpa;

public interface ISeatProjection {
    int getId();
    int getSeatRow();
    int getSeatColumn();
    String getStatus();
}
