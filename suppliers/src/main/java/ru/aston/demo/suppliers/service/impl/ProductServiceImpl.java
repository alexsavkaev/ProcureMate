package ru.aston.demo.suppliers.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.demo.suppliers.dto.ProductDto;
import ru.aston.demo.suppliers.entity.Product;
import ru.aston.demo.suppliers.entity.mapper.ProductMapper;
import ru.aston.demo.suppliers.exception.ResourceNotFoundException;
import ru.aston.demo.suppliers.repository.ProductRepo;
import ru.aston.demo.suppliers.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    @Override
    public List<ProductDto> findAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(product -> productMapper.toDto(product))
                .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product with id " + id + " not found!");
        }
        Product tempProduct = product.get();

        return productMapper.toDto(tempProduct);
    }

    public Map<String, String> save(ProductDto productDto) {
        Optional<Product> productFromRepo = productRepo.findByProductName(productDto.productName());

        if (productFromRepo.isPresent()) {
            throw new RuntimeException("Product with id " + productDto.id() + " already exists!");

        } else {
            Product product = productMapper.toEntity(productDto);
            productRepo.save(product);
        }
        return Map.of("message", "Product saved!");
    }
}
