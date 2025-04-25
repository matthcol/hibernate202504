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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @ManyToMany(mappedBy = "actors")
    private List<Movie> playedMovies = new ArrayList<>();

    public void addDirectedMovie(Movie movie){
        this.getDirectedMovies().add(movie);
    }

    public void removeDirectedMovie(Movie movie){
        this.getDirectedMovies().remove(movie);
    }

    public void addPlayedMovie(Movie movie){
        this.getPlayedMovies().add(movie);
    }

    public void removePlayedMovie(Movie movie){
        this.getPlayedMovies().remove(movie);
    }
}
