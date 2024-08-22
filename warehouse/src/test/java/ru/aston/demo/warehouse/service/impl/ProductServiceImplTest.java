package ru.aston.demo.warehouse.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aston.demo.warehouse.repository.ProductRepository;
import ru.aston.demo.warehouse.service.ProductsService;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {
    @Autowired
    ProductsService productsService;
    @Autowired
    ProductRepository productRepository;

    @Test
    void findById() {
    }

    @Test
    void createProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }
}