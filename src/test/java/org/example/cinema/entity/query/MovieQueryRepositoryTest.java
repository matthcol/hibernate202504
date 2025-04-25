package org.example.cinema.entity.query;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.cinema.entity.Movie;
import org.example.cinema.entity.Movie_;
import org.example.cinema.repository.MovieRepository;
import org.example.cinema.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("datapg")
class MovieQueryRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void testFindAllMovies(){
        movieRepository.findAll()
                .stream()
                .limit(10)
//                .forEach(System.out::println);
                .forEach(movie -> {
                    System.out.println("- " + movie);
                    System.out.println("\t * director: "  + movie.getDirector());
                    System.out.println("\t * actors:");
                    movie.getActors()
                            .forEach(actor -> System.out.println("\t\t~ " + actor));
                });
    }

    @Test
    void testFinOnePerson(){
        int personId = 577654;
        var optPerson = personRepository.findById(personId);
        assertTrue(optPerson.isPresent());
        var person = optPerson.get();
        System.out.println(person);
        System.out.println("has played in:" + person.getPlayedMovies());
        System.out.println("has directed: " + person.getDirectedMovies());
    }

    @Test
    void testFindOneMovie(){
        int movieId = 172495;
        var optMovie = movieRepository.findById(movieId);
        assertTrue(optMovie.isPresent());
        var movie = optMovie.get();
        System.out.println(movie);
        System.out.println("\t * director: "  + movie.getDirector());
        System.out.println("\t * actors:");
        movie.getActors()
                .forEach(actor -> System.out.println("\t\t~ " + actor));
    }

    @Test
    void testFindMovieByTitleYear(){
        String title = "Gladiator";
        int year = 2000;
        var optMovie = movieRepository.findByTitleAndYear(title, year);
        assertTrue(optMovie.isPresent());
        var movie = optMovie.get();
        System.out.println(movie);
        System.out.println("\t * director: "  + movie.getDirector());
        System.out.println("\t * actors:");
        movie.getActors()
                .forEach(actor -> System.out.println("\t\t~ " + actor));
    }

    @Test
    void testFindByDirectorName(){
        String directorName = "eastwood";
        movieRepository.findByDirectorNameContainingIgnoreCase(directorName)
                .forEach(movie -> {
                    System.out.println("- " + movie);
                    System.out.println("\t * director: " + movie.getDirector());
                });
    }

    @ParameterizedTest
    @ValueSource(strings={
            "Clint Eastwood",
            "Quentin Tarantino",
            "Alfred Hitchcock"
    })
    void testFindByDirectorActor(String name){
        movieRepository.findByDirectorActor(name)
                .forEach(movie -> {
                    System.out.println("- " + movie);
                    System.out.println("\t * director: " + movie.getDirector());
                });
    }

    void testFindByExample(){
        // TODO: movieRepository.findAll(example)
    }

    @Test
    void testCriteria(){
        int year = 1984;
        // Criteria builder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // build criteria
        // 1. target (projection)
        CriteriaQuery<Movie> criteriaQuery = cb.createQuery(Movie.class);
        // 2. from (root)
        Root<Movie> root = criteriaQuery.from(Movie.class);
        // 3. selection (where)
        CriteriaQuery<Movie> criteriaQuery2 = criteriaQuery.select(root)
                .where(cb.equal(root.get(Movie_.year), year));

        // finalization: query from criteria
        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery2);

        // execute query and put result into entities or dtos
        query.getResultStream()
                .forEach(System.out::println);
    }
}
