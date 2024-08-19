package ru.aston.demo.orders.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderToAccountingReportDto(
    Long id,
    LocalDateTime movedAt,
    String type,
    List<OrderItemDto> orderItems

) {

}
