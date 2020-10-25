package com.cinema.model.dto;

import lombok.Data;

@Data
public class MovieSessionResponseDto {
    private Long id;
    private String movieTitle;
    private Long cinemaHallId;
    private String showTime;
}
