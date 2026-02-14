package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Seat")
@Table(name = "seat")
@Builder
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "seat_column", nullable = false)
    int seatColumn;
    @Column(name = "seat_row", nullable = false)
    int seatRow;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private SeatTypeEntity type;

}
