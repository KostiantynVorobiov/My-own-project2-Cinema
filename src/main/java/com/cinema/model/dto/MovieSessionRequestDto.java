package com.cinema.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class MovieSessionRequestDto {
    @Positive(message = "Can be numbers greater then 0")
    private Long movieId;
    @Positive(message = "Can be numbers greater then 0")
    private Long cinemaHallId;
    @NotEmpty(message = "Show Time can't be empty")
    private String showTime;
}
