package ru.aston.demo.suppliers.service;

import org.springframework.stereotype.Service;
import ru.aston.demo.suppliers.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAllProducts();
    ProductDto findById(Long id);


}
