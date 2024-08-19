package ru.aston.demo.orders.dto;

import java.math.BigDecimal;

public record ProductDto(
    Long id,
    String productName,
    BigDecimal price,
    SupplierDto supplierDto) {

}