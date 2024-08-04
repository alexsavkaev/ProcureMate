package ru.aston.demo.suppliers.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.aston.demo.suppliers.dto.ProductDto;
import ru.aston.demo.suppliers.entity.Product;
import ru.aston.demo.suppliers.exception.ResourceNotFoundException;
import ru.aston.demo.suppliers.repository.ProductRepo;
import ru.aston.demo.suppliers.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepo productRepo;
    private ModelMapper modelMapper;

    @Override
    public List<ProductDto> findAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product with id " + id + " not found!");
        }
        return modelMapper.map(product.get(), ProductDto.class);
    }
}
