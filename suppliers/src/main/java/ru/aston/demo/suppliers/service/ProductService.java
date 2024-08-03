package ru.aston.demo.suppliers.service;

import ru.aston.demo.suppliers.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAllProducts();
    ProductDto findById(Long id);


}
