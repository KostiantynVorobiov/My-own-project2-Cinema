package com.cinema.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class MovieSessionRequestDto {
    @Positive(message = "Movie id must be greater than 0")
    private Long movieId;
    @Positive(message = "Cinema hall id must be greater than 0")
    private Long cinemaHallId;
    @NotEmpty(message = "Show Time can't be empty")
    private String showTime;
}
