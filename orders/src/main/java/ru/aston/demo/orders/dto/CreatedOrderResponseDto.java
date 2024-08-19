package ru.aston.demo.orders.dto;

import java.util.List;

public record CreatedOrderResponseDto (
    Long id,
    String details,
    SupplierDto supplier,
    List<OrderItemDto> orderItems
){

}
