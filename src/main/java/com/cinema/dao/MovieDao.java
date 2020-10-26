package com.cinema.dao;

import com.cinema.model.Movie;
import java.util.List;

public interface MovieDao {
    Movie add(Movie movie);

    public Movie get(Long id);

    List<Movie> getAll();
}
