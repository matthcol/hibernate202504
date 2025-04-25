package org.example.cinema.repository;

import org.example.cinema.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Override
    @EntityGraph("Movie.withDirectorAndActors")
    Optional<Movie> findById(Integer movieId);

    // Spring Data writes automatically queries with following vocabulary
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    @EntityGraph("Movie.withDirectorAndActors")
    Optional<Movie> findByTitleAndYear(String title, int year);

    @EntityGraph(attributePaths = "director")
    List<Movie> findByDirectorNameContainingIgnoreCase(String directorName);

    // JPQL/HQL
    @Query("""
    SELECT m 
    FROM Movie m 
        JOIN FETCH m.director d 
        JOIN m.actors a
    WHERE
        d.name = :name
        AND a.name = :name
    """)
    List<Movie> findByDirectorActor(String name);
}
