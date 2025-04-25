package org.example.cinema.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

// lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
// JPA
@Embeddable
public class PlayId {
    private int movieId;
    private int actorId;
}
