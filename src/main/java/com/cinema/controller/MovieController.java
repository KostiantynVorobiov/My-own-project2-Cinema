package com.cinema.controller;

import com.cinema.model.Movie;
import com.cinema.model.dto.model.MovieRequestDto;
import com.cinema.model.dto.model.MovieResponseDto;
import com.cinema.model.mapper.MovieDtoMapper;
import com.cinema.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieDtoMapper movieDtoMapper;

    @Autowired
    public MovieController(MovieService movieService, MovieDtoMapper movieDtoMapper) {
        this.movieService = movieService;
        this.movieDtoMapper = movieDtoMapper;
    }

    @GetMapping("/inject")
    public void inject() {
        Movie saw = new Movie();
        saw.setTitle("Saw V");
        saw.setDescription("Horror and bullying");
        movieService.add(saw);
    }

    @GetMapping
    public List<MovieResponseDto> getAllMovie() {
        return movieService.getAll().stream()
                .map(movieDtoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public String addMovie(@RequestBody MovieRequestDto movieRequestDto) {
        movieService.add(movieDtoMapper.fromRequestDto(movieRequestDto));
        return "Okay";
    }
}
