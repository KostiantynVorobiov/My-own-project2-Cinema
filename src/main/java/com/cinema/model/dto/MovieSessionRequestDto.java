package com.cinema.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class MovieSessionRequestDto {
    @NotEmpty(message = "Movie id can't be empty")
    @Positive(message = "Can be numbers greater then 0")
    private Long movieId;
    @NotEmpty(message = "Cinema Hall id can't be empty")
    @Positive(message = "Can be numbers greater then 0")
    private Long cinemaHallId;
    @NotNull(message = "Show Time can't be empty")
    private String showTime;
}
