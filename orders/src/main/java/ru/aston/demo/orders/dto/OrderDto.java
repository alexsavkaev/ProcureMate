package ru.aston.demo.orders.dto;

import ru.aston.demo.orders.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link ru.aston.demo.orders.entity.Order}
 */
public record OrderDto(
    Long id,
    String details,
    LocalDateTime creationTime,
    Status status,
    SupplierDto supplier,
    List<OrderItemDto> orderItems) {

}