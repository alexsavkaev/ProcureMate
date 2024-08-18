package ru.aston.demo.suppliers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.aston.demo.suppliers.dto.ProductDto;
import ru.aston.demo.suppliers.service.impl.ProductServiceImpl;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping("/")
    public List<ProductDto> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductDtoById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }


}
