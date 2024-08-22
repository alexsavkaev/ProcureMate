package ru.aston.demo.warehouse.dto;

import ru.aston.demo.warehouse.model.MovementType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StockMovementDto
        (Long id,
         long productId,
         String productName,
         int quantity,
         BigDecimal price,
         LocalDate movedAt,
         MovementType movement_type
        ) {
}
