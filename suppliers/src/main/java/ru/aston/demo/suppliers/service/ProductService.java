package ru.aston.demo.suppliers.service;

import ru.aston.demo.suppliers.dto.ProductDto;

import java.util.List;
import java.util.Map;

public interface ProductService {

    List<ProductDto> findAllProducts();
    ProductDto findById(Long id);
    Map<String, String> saveToDb(ProductDto productDto);
    Map<String, String> delete(Long id);
    Map<String, String> update(ProductDto productDto);

    List<ProductDto> findBySupplierName(String supplierName);
}
