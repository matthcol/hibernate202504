package org.example.cinema.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

// Lombok
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString(of={"id", "title", "year"}) // NB: avoid association fields => FETCH
// JPA
@Entity // mandatory
@Table(
        // custom settings (not mandatory)
        // name = "t_movie",
        schema = "cinema",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "year"})
)
public class Movie {

    @Id // primary key
    // strategy og key generation:
    // - identity: DB generates PK (autoincrement, serial, identity, trigger, ...)
    //          => implicit sequence
    // - sequence: explicit sequence (DDL), fetch id as soon as possible
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    // NB: Object type let the filed null before persisting the entity
    //      primitive type set the field to 0 before persisting the entity

    // NB: Not Null management:
    // - JPA: @Column:nullable false => SQL NOT NULL, pre-validated by Hibernate
    // - Lombok: @NonNull => validated by auto-generated constructor(s) and setter
    @NonNull
    @Column(nullable = false, length = 300) // custom settings for a column (not mandatory)
    private String title;

    // @Column(name = "\"year\"", nullable = false) // implicit with primitive type
    @Column(nullable = false) // implicit with primitive type
    private int year;

    // nullable field => Object wrapper type instead of primitive type
    private Integer duration;

    // SQL: type text Postgres, mssql, mysql ou clob chez Oracle
    // @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "text")
    // @Lob
    private String synopsis;

    // @Transient // non-persistent field: debug, prototype, new feature
    @ManyToOne
    @JoinColumn(name = "director_id") // FK name, nullable
    private Person director;

    @Builder.Default
    // @Singular // actor one by one
    @ManyToMany
    @JoinTable(
            name = "play",
            // NB: check FK constraints to avoid mistakes
            inverseJoinColumns = @JoinColumn(name="actor_id"),
            joinColumns = @JoinColumn(name="movie_id")
    )
    private List<Person> actors = new ArrayList<>(); // with @BuilderDefault

    // NB: equals
    // default strategy: don't write equals method (HBN uses address and id)
    // with embedded/composite field => custom equals for the class representing field

    public void addActor(Person actor){
        this.getActors().add(actor);
    }
}
