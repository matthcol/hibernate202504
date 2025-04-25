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
@NamedEntityGraph(
        name = "Movie.withDirectorAndActors",
        attributeNodes = {
                @NamedAttributeNode("director"),
                @NamedAttributeNode(value = "plays", subgraph = "plays-sub")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "plays-sub",
                        attributeNodes = @NamedAttributeNode("actor")
                )
        }
)
public class Movie {

    @Id // primary key
    // strategy og key generation:
    // - identity: DB generates PK (autoincrement, serial, identity, trigger, ...)
    //          => implicit sequence
    // - sequence: explicit sequence (DDL), fetch id as soon as possible
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // custom sequence, replace strategy=sequence by generator = + @SequenceGenerator
    @GeneratedValue(generator = "movie_seq")
    @SequenceGenerator(name = "movie_seq", sequenceName = "movie_id_seq", allocationSize = 1)
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
    @ManyToOne(fetch = FetchType.LAZY) // default EAGER
    @JoinColumn(name = "director_id") // FK name, nullable
    private Person director;

    @Builder.Default
    @OneToMany(mappedBy = "movie")
    private List<Play> plays = new ArrayList<>(); // with @BuilderDefault

    public void addPlay(Play play){
        this.getPlays().add(play);
    }
}
