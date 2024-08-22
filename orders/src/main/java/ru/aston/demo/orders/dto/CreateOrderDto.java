package ru.aston.demo.orders.dto;

import java.util.List;

public record CreateOrderDto(
    String details,
    Long supplierId,
    List<OrderItemDto> orderItems
) {

}