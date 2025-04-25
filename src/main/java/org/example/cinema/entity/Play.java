package org.example.cinema.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// lombok
@Getter @Setter
@NoArgsConstructor
// JPA
@Entity
public class Play {

    @EmbeddedId
    private PlayId id;

    private String role;

    public Play(Movie movie, Person actor, String role) {
        this.role = role;
        this.movie = movie;
        this.actor = actor;
        this.id = new PlayId(movie.getId(), actor.getId());
    }

    @ManyToOne
    @MapsId("movieId")
    private Movie movie;

    @ManyToOne
    @MapsId("actorId")
    private Person actor;
}
