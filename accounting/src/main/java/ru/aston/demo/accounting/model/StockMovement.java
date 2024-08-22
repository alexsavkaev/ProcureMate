package ru.aston.demo.accounting.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import ru.aston.demo.accounting.type.MovementType;

import java.math.BigDecimal;

public record StockMovement(
        Long id,
        long productId,
        @NotBlank String productName,
        @Positive long quantity,
        @NotNull @Positive BigDecimal price,
        @NotNull LocalDateTime movedAt,
        @NotNull MovementType type) {

}
