package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "seattype")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "price", nullable = false)
    BigDecimal price;

}
