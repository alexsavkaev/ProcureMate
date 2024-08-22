package ru.aston.demo.warehouse.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.demo.warehouse.dto.ProductsDto;
import ru.aston.demo.warehouse.service.ProductsService;

@RestController
@Getter
@RequiredArgsConstructor
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @GetMapping("/getProduct/{productId}")
    public ProductsDto getProduct(@PathVariable(value = "productId") Long productId) {
        ProductsDto productsDto= productsService.findById(productId);
        return productsDto;
    }


    @PostMapping("/createProduct")
    public ResponseEntity<ProductsDto> createProduct(@RequestBody ProductsDto productsDto) {
        ProductsDto saved = productsService.createProduct(productsDto);
        if (saved != null) {
            return ResponseEntity.ok(saved);
        } else
            return ResponseEntity.badRequest().build();
    }




//    @PutMapping("/updateProduct/{productId}")
//    public ResponseEntity<ProductsDto> updateProduct(@RequestBody ProductsDto productsDto,@PathVariable Long productId) {
//        ProductsDto productUpdate = productsService.updateProduct(productsDto, productId);
//        if (productUpdate != null) {
//            return ResponseEntity.ok(productUpdate);
//        } else
//            return ResponseEntity.badRequest().build();
//    }

    @DeleteMapping("/deleteProduct/{productId}")
    public void deleteProduct(@PathVariable(value = "productId") Long productId) {
        productsService.deleteProduct(productId);
    }
}
