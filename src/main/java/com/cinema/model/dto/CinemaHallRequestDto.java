package com.cinema.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class CinemaHallRequestDto {
    @NotEmpty(message = "Capacity can't be empty")
    @Positive(message = "Can be numbers greater then 0")
    private int capacity;
    @NotNull(message = "Should be description for cinema hall")
    private String description;
}
