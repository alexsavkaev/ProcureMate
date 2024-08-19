package ru.aston.demo.suppliers.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.demo.suppliers.dto.ProductDto;
import ru.aston.demo.suppliers.entity.Product;
import ru.aston.demo.suppliers.entity.Supplier;
import ru.aston.demo.suppliers.entity.mapper.ProductMapper;
import ru.aston.demo.suppliers.exception.ResourceAlreadyExistException;
import ru.aston.demo.suppliers.exception.ResourceNotFoundException;
import ru.aston.demo.suppliers.repository.ProductRepo;
import ru.aston.demo.suppliers.repository.SupplierRepo;
import ru.aston.demo.suppliers.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final SupplierRepo supplierRepo;

    @Override
    public List<ProductDto> findAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(product -> productMapper.toDto(product))
                .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product with id " + id + " not found!");
        }
        return productMapper.toDto(optionalProduct.get());
    }


    @Override
    public Map<String, String> saveToDb(ProductDto productDto) {
        Optional<Product> optionalProduct = productRepo.findByProductName(productDto.productName());
        if (Objects.nonNull(productDto.id())) {
            throw new ResourceAlreadyExistException("Product with id: " + productDto.id() + " already exists!");
        }
        if (optionalProduct.isEmpty()) {
            Product product = productMapper.toEntity(productDto);

            Optional<Supplier> optionalSupplier =
                    supplierRepo.findBySupplierName(productDto.supplierDto().supplierName());
            if (optionalSupplier.isEmpty()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierName(productDto.supplierDto().supplierName());
                supplierRepo.save(supplier);
                product.setSupplier(supplier);
            } else {
                product.setSupplier(optionalSupplier.get());
            }

            productRepo.save(product);
        } else {
            throw new ResourceAlreadyExistException("Product name already exists!");
        }
        return Map.of("message", "Product saved!");
    }

    @Override
    public Map<String, String> delete(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product with id " + id + " not found!");
        }
        productRepo.deleteById(id);
        return Map.of("message", "Product " + "with id " + id + " deleted!");
    }

    @Override
    public Map<String, String> update(ProductDto productDto) {
        Optional<Product> optionalProduct = productRepo.findById(productDto.id());
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product with id " + productDto.id() + " not found!");
        }
        Product product = productMapper.toEntity(productDto);
        productRepo.save(product);
        return Map.of("message", "Product " + "with id " + productDto.id() + " updated!");
    }

    @Override
    public List<ProductDto> findBySupplierName(String supplierName) {
        List<Product> bySupplierSupplierName = productRepo.findBySupplier_SupplierName(supplierName);
        if (bySupplierSupplierName.isEmpty()) {
            throw new ResourceNotFoundException("Product with supplier name " + supplierName + " not found!");
        }
        return bySupplierSupplierName
                .stream()
                .map(product -> productMapper.toDto(product))
                .toList();
    }
}


