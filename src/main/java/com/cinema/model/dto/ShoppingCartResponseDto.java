package com.cinema.model.dto;

import com.cinema.model.Ticket;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private List<Ticket> tickets;
    private String userEmail;
}
