package ru.aston.demo.suppliers.dto;

import java.math.BigDecimal;

public record ProductDto(Long id, String productName, BigDecimal price, SupplierDto supplierDto) {
}
