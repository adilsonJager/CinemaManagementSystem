package dev.adilsonmagalhaesjager.cinemamanagementsystem_adilsonmagalhaes.model;


import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "movie")
@Entity(name = "movie")
@Builder
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "time", nullable = false)
    private Integer time;

    @Column(name = "synopsis", nullable = false)
    String synopsis;

    @Column(name = "genre", nullable = false)
    String genre;

    @Column(name = "classification", nullable = false)
    Integer classification;

    @Column(name = "poster_url", nullable = false)
    String posterUrl;

}
