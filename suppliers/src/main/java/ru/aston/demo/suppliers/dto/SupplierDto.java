package ru.aston.demo.suppliers.dto;

import ru.aston.demo.suppliers.entity.Product;

import java.util.List;

public record
SupplierDto(Long id, String supplierName, List<Product> productList) {

}
