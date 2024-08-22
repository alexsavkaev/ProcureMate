package ru.aston.demo.accounting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import ru.aston.demo.accounting.type.MovementType;

import java.math.BigDecimal;

public record StockMovementDto(
        Long id,
        long productId,

        @JsonProperty("productName")
        String productName,
        long quantity,
        BigDecimal price,
        @JsonProperty("movedAt")
        LocalDateTime movedAt,
        MovementType type) {

}
