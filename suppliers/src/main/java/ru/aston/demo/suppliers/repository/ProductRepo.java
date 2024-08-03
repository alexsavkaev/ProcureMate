package ru.aston.demo.suppliers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aston.demo.suppliers.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
}
