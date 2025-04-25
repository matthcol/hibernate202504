package org.example.cinema.entity.crud;

import org.example.cinema.entity.Movie;
import org.example.cinema.repository.MovieRepository;
import org.example.cinema.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("datapg")
class MovieCrudNonEmptyDatabaseTest {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PersonRepository personRepository;

    @Test
    void testNewEntities(){
        var movie = Movie.builder()
                .title("Oppenheimer")
                .year(2023)
                .build();
        movieRepository.saveAndFlush(movie);
        System.out.println(movie);
    }

    @Test
    void testSetNull(){
        personRepository.deleteById(631);
        personRepository.flush();

        var optMovie = movieRepository.findById(172495);
        assertTrue(optMovie.isPresent());

        var movie = optMovie.get();
        System.out.println(movie);

        assertNull(movie.getDirector());
    }

    @Test
    void testDeleteCascade(){
        movieRepository.deleteById( 172495);
        movieRepository.flush(); // delete play rows then the movie

        // TODO: assert all persons playing in this movie still exists
    }
}
