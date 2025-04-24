package org.example.cinema.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void testConstructorDefault(){
        // this constructor (and only this one) does not check contracts (null/not null, ...)
        // => compatible with frameworks as JPA, JSON/XML serialization
        var movie = new Movie();
        assertNull(movie.getId());
        assertNull(movie.getTitle()); // contract not null is not verified
    }

//    @Test
//    void testConstructorAll_notNullField_ko(){
//        // missing title
//        assertThrows(NullPointerException.class, () -> new Movie(1, null, 1980, null, null));
//    }

    @Test
    void testBuilder_notNullField_ko(){
        assertThrows(NullPointerException.class, () ->
                Movie.builder()
                .id(1)
                // missing title
                .build());
    }



}