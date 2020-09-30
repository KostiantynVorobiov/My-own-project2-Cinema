package com.cinema;

import com.cinema.lib.Injector;
import com.cinema.model.Movie;
import com.cinema.service.MovieService;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);

        Movie movie = new Movie();
        movieService.getAll().forEach(System.out::println);

        movie.setTitle("Fast and Furious");
        movie.setDescription("Action movie about cars, races and fights");
        movieService.add(movie);

        movieService.getAll().forEach(System.out::println);
    }
}
