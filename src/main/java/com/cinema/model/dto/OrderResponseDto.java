package com.cinema.model.dto;

import com.cinema.model.Ticket;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private String userEmail;
    private List<Ticket> tickets;
    private LocalDateTime orderDate;
}
