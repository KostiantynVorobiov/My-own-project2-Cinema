package com.cinema.dao;

import com.cinema.model.Movie;
import java.util.List;

public interface MovieDao {
    Movie add(Movie movie);

    public Movie getById(Long id);

    List<Movie> getAll();
}
