package ru.aston.demo.suppliers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aston.demo.suppliers.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(String name);
    List<Product> findBySupplier_SupplierName(String supplierName);
}
