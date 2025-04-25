package org.example.cinema.tools;

import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;

public class JpaTools {

    @SuppressWarnings("unchecked")
    public static <T,U> Join<T,U> castFetch2Join(Fetch<T,U> fetch) {
        return (Join<T,U>) fetch;
    }
}
