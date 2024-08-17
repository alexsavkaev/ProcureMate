package ru.aston.demo.orders.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderToAccountingReportDto(
    Long id,
    Long productId,
    String productName,
    int quantity,
    BigDecimal productPrice,
    LocalDateTime movedAt,
    String type

) {

}
