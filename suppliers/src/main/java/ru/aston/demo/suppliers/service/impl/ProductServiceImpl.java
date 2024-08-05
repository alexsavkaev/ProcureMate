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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    @Override
    public List<ProductDto> findAllProducts() {
        return null;
//        .findAll()
//                .stream()
//                .map(product -> modelMapper.map(product, ProductDto.class))
//                .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product with id " + id + " not found!");
        }
        Product tempProduct = product.get();

//        SupplierDto tempDto = supplierMapper.supplierDto(tempProduct.getSupplier());

        return productMapper.toDto(tempProduct);
    }
}
