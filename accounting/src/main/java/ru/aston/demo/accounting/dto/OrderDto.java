package ru.aston.demo.accounting.dto;

import lombok.Getter;
import ru.aston.demo.accounting.type.MovementType;

import java.time.Instant;
import java.util.List;


public record OrderDto(
        long id,
        Instant movedAt,
        MovementType type,
        List<OrderItemDto> orderItems
) {
}
