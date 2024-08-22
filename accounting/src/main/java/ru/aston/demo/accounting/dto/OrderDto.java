package ru.aston.demo.accounting.dto;

import java.time.LocalDateTime;
import ru.aston.demo.accounting.type.MovementType;
import java.util.List;


public record OrderDto(
        long id,
        LocalDateTime movedAt,
        MovementType type,
        List<OrderItemDto> orderItems
) {
}
