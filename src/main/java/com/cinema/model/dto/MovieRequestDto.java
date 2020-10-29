package com.cinema.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieRequestDto {
    @NotNull(message = "Should be movie title")
    private String title;
    @NotNull(message = "Should be movie description")
    private String description;
}
