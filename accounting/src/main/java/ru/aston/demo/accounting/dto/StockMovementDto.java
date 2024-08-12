package ru.aston.demo.accounting.dto;

import ru.aston.demo.accounting.type.MovementType;

import java.math.BigDecimal;
import java.time.Instant;

public record StockMovementDto(
        Long id,
        long productId,
        String productName,
        long quantity,
        BigDecimal price,
        Instant movedAt,
        MovementType type) {

}
