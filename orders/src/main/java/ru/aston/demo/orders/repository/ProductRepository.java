package ru.aston.demo.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.demo.orders.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
