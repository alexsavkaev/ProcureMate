package ru.aston.demo.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aston.demo.warehouse.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
