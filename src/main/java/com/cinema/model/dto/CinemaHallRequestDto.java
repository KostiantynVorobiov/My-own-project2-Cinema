package com.cinema.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class CinemaHallRequestDto {
    @Positive(message = "Capacity must be greater than 0")
    private int capacity;
    @NotNull(message = "Description can't be null")
    @NotEmpty(message = "Should be description for cinema hall")
    private String description;
}
