package ru.aston.demo.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.demo.accounting.entity.StockMovementEntity;

import java.time.Instant;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovementEntity, Long> {

    List<StockMovementEntity> findByMovedAtBetween(Instant movedAtFrom, Instant movedAtTo);
}
