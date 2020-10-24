package com.cinema.model.dto.model;

import com.cinema.model.CinemaHall;
import com.cinema.model.Movie;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieSessionRequestDto {
    private Movie movie;
    private CinemaHall cinemaHall;
    private LocalDateTime showTime;
}