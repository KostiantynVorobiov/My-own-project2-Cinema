package com.cinema.service.mapper;

import com.cinema.model.Order;
import com.cinema.model.Ticket;
import com.cinema.model.dto.OrderResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDto convertToResponseDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setUserEmail(order.getUser().getEmail());
        List<Long> tickets = order.getTickets().stream()
                .map(Ticket::getId)
                .collect(Collectors.toList());
        orderResponseDto.setTickets(tickets);
        orderResponseDto.setOrderDate(order.getOrderDate());
        return orderResponseDto;
    }
}
