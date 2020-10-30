package com.cinema.model.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MovieRequestDto {
    @NotEmpty(message = "Should be movie title")
    private String title;
    @NotEmpty(message = "Should be description for movie")
    private String description;
}
