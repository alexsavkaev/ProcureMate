package ru.aston.demo.warehouse.service;

import ru.aston.demo.warehouse.dto.ProductsDto;

public interface ProductsService {
    ProductsDto findById(Long id);

    ProductsDto createProduct(ProductsDto productsDto);
//   ProductsDto updateProduct(ProductsDto productsDto,Long id);
    ProductsDto deleteProduct(Long id);


}
