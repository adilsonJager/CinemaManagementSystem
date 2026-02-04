package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model;

import dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "reservation")
@Entity(name = "reservation")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private ShowtimeEntity showtime;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private SeatEntity seat;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

}
