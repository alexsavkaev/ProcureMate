package ru.aston.demo.orders.dto;

import java.time.LocalDateTime;
import java.util.List;
import ru.aston.demo.orders.entity.OrderItem;

public record OrderToWarehouseReport(
    Long orderId,
    List<OrderItem> orderItems,
    LocalDateTime movedAt
) {
}
