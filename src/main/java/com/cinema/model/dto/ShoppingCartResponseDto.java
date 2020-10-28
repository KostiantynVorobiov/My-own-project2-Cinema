package com.cinema.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private List<Long> tickets;
    private String userEmail;
}
