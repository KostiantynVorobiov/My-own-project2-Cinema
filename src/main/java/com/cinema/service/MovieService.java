package com.cinema.service;

import com.cinema.model.Movie;
import java.util.List;

public interface MovieService {
    Movie add(Movie movie);

    Movie getById(Long id);

    List<Movie> getAll();
}
