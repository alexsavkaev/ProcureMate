package ru.aston.demo.suppliers.dto;

import ru.aston.demo.suppliers.entity.Supplier;

public record ProductDto(Long id, String productName, Supplier supplier) {
}
