package org.example.cinema.entity.crud;

import org.example.cinema.entity.Movie;
import org.example.cinema.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("datapg")
class MovieCrudNonEmptyDatabaseTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    void testNewEntities(){
        var movie = Movie.builder()
                .title("Oppenheimer")
                .year(2023)
                .build();
        movieRepository.saveAndFlush(movie);
        System.out.println(movie);
    }
}
