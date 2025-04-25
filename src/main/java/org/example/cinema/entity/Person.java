package org.example.cinema.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of={"id", "name", "birthdate"})
// JPA
@Entity
@Table(schema = "cinema")
public class Person {

    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_id_seq", allocationSize = 1)
    private Integer id;

    @Column(nullable = false, length= 30)
    private String name;

    // SQL: type Date is automatically chosen (ISO 8601)
    private LocalDate birthdate;

    // Legacy types for temporal data, mapping must be oriented to wright db type
    // @Temporal(TemporalType.DATE)
    // private Date birthdate;
    // private Calendar birthdate;

    @Builder.Default
    @OneToMany(mappedBy = "director")
    private List<Movie> directedMovies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Play> plays = new ArrayList<>();

    public void addDirectedMovie(Movie movie){
        this.getDirectedMovies().add(movie);
    }

    public void removeDirectedMovie(Movie movie){
        this.getDirectedMovies().remove(movie);
    }

    public void addPlay(Play play){
        this.getPlays().add(play);
    }
}
