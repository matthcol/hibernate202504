package org.example.cinema.entity;

import jakarta.persistence.*;
import lombok.*;

// Lombok
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString(of={"id", "title", "year"}) // NB: avoid association fields => FETCH
// JPA
@Entity
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
    @Column(nullable = false, length = 300)
    private String title;

    // @Column(name = "\"year\"", nullable = false) // implicit with primitive type
    @Column(nullable = false) // implicit with primitive type
    private int year;

    // nullable field => Object wrapper type instead of primitive type
    private Integer duration;

    @Transient // non-persistent field: debug, prototype, new feature
    private Object director;

    // NB: equals
    // default strategy: don't write equals method (HBN uses address and id)
    // with embedded/composite field => custom equals for the class representing field
}
