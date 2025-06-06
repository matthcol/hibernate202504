package org.example.cinema.entity.crud;

import jakarta.persistence.EntityManager;
import org.example.cinema.entity.Movie;
import org.example.cinema.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@DataJpaTest // H2 by default, replacing DB configured in the application
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("tupg")
class MovieCrudEmptyDatabaseTest {

    // 3 choices to access Hibernate session:

    @Autowired
    EntityManager entityManager;
    // TestEntityManager testEntityManager;
    // MovieRepositoty movieRepositoty;

    @Test
    // @Rollback(false) // useful to debug ...
    void testSaveSimple(){
        // begin transaction auto (Spring JPA test)
        var movie = Movie.builder()
                .title("Gladiator")
                .year(2000)
                .build();

        entityManager.persist(movie); // SQL: read sequence (strategy sequence) or maybe insert (strategy identity)
        System.out.println(movie);
        entityManager.flush(); // SQL: insert if not done before
        System.out.println(movie);
    } // end transaction (Spring JPA test) => Rollback


    @Test
    // @Rollback(false) // view data in the database after the test
    void testSaveWithPersons() {
        var director = Person.builder()
                .name("Ridley Scott")
                .build();
        var actor1 = Person.builder()
                .name("Russel Crowe")
                .birthdate(LocalDate.of(1964, 4, 7))
                .build();
        var actor2 = Person.builder()
                .name("Joaquin Phoenix")
                .build();
        // persist persons before persisting movie
        Stream.of(director, actor1, actor2)
                .forEach(entityManager::persist);
        entityManager.flush();

        var movie = Movie.builder()
                .title("Gladiator")
                .year(2000)
                .director(director)
                .build();
        movie.addActor(actor1);
        movie.addActor(actor2);
        // bidirectional reverse links:
        director.addDirectedMovie(movie);
        Stream.of(actor1, actor2)
                .forEach(actor -> actor.addPlayedMovie(movie));

        entityManager.persist(movie);

        entityManager.flush();
        System.out.println(movie);
    }

    @Test
        // @Rollback(false) // view data in the database after the test
    void testSaveWithPersons2() {
        var director = Person.builder()
                .name("Ridley Scott")
                .build();
        var actor1 = Person.builder()
                .name("Russel Crowe")
                .birthdate(LocalDate.of(1964, 4, 7))
                .build();
        var actor2 = Person.builder()
                .name("Joaquin Phoenix")
                .build();

        var movie = Movie.builder()
                .title("Gladiator")
                .year(2000)
                .build();

        // persist independent objects
        Stream.of(director, movie, actor1, actor2)
                .forEach(entityManager::persist);
        entityManager.flush();

        // relation director after persistence
        movie.setDirector(director);
        director.addDirectedMovie(movie);
        entityManager.flush();

        // relation play after persistence
        Stream.of(actor1, actor2)
                .forEach(actor -> {
                    movie.addActor(actor);
                    actor.addPlayedMovie(movie);
                });
        entityManager.flush();
    }



}