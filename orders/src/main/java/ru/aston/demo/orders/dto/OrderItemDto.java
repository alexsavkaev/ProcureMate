package ru.aston.demo.orders.dto;


import java.math.BigDecimal;

/**
 * DTO for {@link ru.aston.demo.orders.entity.OrderItem}
 */
public record OrderItemDto(
    Long id,
    int quantity,
    Long productId,
    BigDecimal productPrice) {

}