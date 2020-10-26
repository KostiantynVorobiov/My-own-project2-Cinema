package com.cinema.service.mapper;

import com.cinema.model.Order;
import com.cinema.model.dto.OrderResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDto convertToResponseDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setUserEmail(order.getUser().getEmail());
        orderResponseDto.setTickets(order.getTickets());
        orderResponseDto.setOrderDate(order.getOrderDate());
        return orderResponseDto;
    }
}
