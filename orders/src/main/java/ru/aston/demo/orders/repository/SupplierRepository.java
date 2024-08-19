package ru.aston.demo.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.demo.orders.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
