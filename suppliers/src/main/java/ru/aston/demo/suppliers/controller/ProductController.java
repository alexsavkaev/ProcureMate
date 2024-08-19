package ru.aston.demo.suppliers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aston.demo.suppliers.dto.ProductDto;
import ru.aston.demo.suppliers.service.impl.ProductServiceImpl;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/supplier-service/products")
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/get-product/{id}")
    public ProductDto getProductDtoById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @GetMapping("/get-product/by-supplier/{supplierName}")
    public List<ProductDto> getProductsBySupplier(@PathVariable("supplierName") String supplierName) {
        return productService.findBySupplierName(supplierName);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> delete(@PathVariable("id") Long id) {
        return productService.delete(id);
    }

    @PatchMapping("/update")
    public Map<String, String> update(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }
    @PostMapping("/save")
    public Map<String, String> save(@RequestBody ProductDto productDto) {
        return productService.saveToDb(productDto);
    }
}
