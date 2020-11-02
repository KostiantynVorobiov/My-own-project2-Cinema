package com.cinema.model.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MovieRequestDto {
    @NotEmpty(message = "Movie title can't be empty")
    private String title;
    @NotEmpty(message = "Description can't be empty")
    private String description;
}
