package ru.aston.demo.suppliers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.demo.suppliers.dto.ProductDto;
import ru.aston.demo.suppliers.service.impl.ProductServiceImpl;

import java.util.List;
import java.util.Map;


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

//    @PostMapping("/")
//    public void createProduct(@RequestBody ProductDto productDto) {
//        productService.save(productDto);
//    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.saveToDb(productDto));
    }

}
