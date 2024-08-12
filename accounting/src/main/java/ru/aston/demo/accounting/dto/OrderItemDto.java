package ru.aston.demo.accounting.dto;

import java.math.BigDecimal;


public record OrderItemDto(
        Long id,
        Long quantity,
        Long productId,
        String itemName,
        BigDecimal productPrice
) {
}
