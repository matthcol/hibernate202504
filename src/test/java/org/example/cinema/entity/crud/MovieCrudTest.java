package org.example.cinema.entity.crud;

import jakarta.persistence.EntityManager;
import org.example.cinema.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@DataJpaTest // H2 by default, replacing DB configured in the application
@AutoConfigureTestDatabase(replace = NONE)
class MovieCrudTest {

    // 3 choices to access Hibernate session:

    @Autowired
    EntityManager entityManager;
    // TestEntityManager testEntityManager;
    // MovieRepositoty movieRepositoty;

    @Test
    // @Rollback(false)
    void testSave(){
        // begin transaction auto (Spring JPA test)
        var movie = Movie.builder()
                .title("Gladiator")
                .year(2000)
                .build();

        entityManager.persist(movie); // SQL: read sequence (strategy sequence) or maybe insert (strategy identity)
        System.out.println(movie);
        entityManager.flush(); // SQL: insert if not done before
    } // end transaction (Spring JPA test) => Rollback


}