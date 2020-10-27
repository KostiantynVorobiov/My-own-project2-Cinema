package com.cinema.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDto {
    private Long id;
    private String userEmail;
    private List<Long> tickets;
    private LocalDateTime orderDate;
}
