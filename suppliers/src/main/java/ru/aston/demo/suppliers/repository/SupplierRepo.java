package ru.aston.demo.suppliers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aston.demo.suppliers.entity.Supplier;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Long> {
}
