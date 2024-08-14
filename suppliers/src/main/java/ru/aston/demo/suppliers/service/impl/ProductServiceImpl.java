package ru.aston.demo.suppliers.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.demo.suppliers.dto.ProductDto;
import ru.aston.demo.suppliers.entity.Product;
import ru.aston.demo.suppliers.entity.Supplier;
import ru.aston.demo.suppliers.entity.mapper.ProductMapper;
import ru.aston.demo.suppliers.exception.ResourceNotFoundException;
import ru.aston.demo.suppliers.repository.ProductRepo;
import ru.aston.demo.suppliers.repository.SupplierRepo;
import ru.aston.demo.suppliers.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class
ProductServiceImpl implements ProductService {
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
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product with id " + id + " not found!");
        }
        Product tempProduct = product.get();

        return productMapper.toDto(tempProduct);
    }

    @Override
    public Map<String, String> saveToDb(ProductDto productDto) {
        Optional<Product> optionalProduct = productRepo.findByProductName(productDto.productName());
        if (optionalProduct.isEmpty()) {
            Product product = productMapper.toEntity(productDto);

            // Check if the supplier exists, if not create a new one
            Optional<Supplier> optionalSupplier = supplierRepo.findBySupplierName(productDto.supplierDto().supplierName());
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
            throw new RuntimeException("Product name already exists!");
        }
        return Map.of("message", "Product saved!");
    }


}
