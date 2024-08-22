package ru.aston.demo.accounting.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.aston.demo.accounting.entity.StockMovementEntity;
import ru.aston.demo.accounting.mapper.StockMovementMapper;
import ru.aston.demo.accounting.model.StockMovement;
import ru.aston.demo.accounting.repository.StockMovementRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class StockMovementService {

    private final StockMovementRepository movementRepository;
    private final StockMovementMapper movementMapper;

    public List<StockMovement> findByMovedAtBetween(Instant movedAtFrom, Instant movedAtTo) {
        return movementRepository
                .findByMovedAtBetween(movedAtFrom, movedAtTo)
                .stream()
                .map(movementMapper::toStockMovement)
                .toList();
    }

    public long create(@Valid StockMovement movement) {
        StockMovementEntity createdMovement = movementRepository.save(
            movementMapper.toStockMovementEntity(movement)
        );

        return createdMovement.getId();
    }

    public void delete(long movementId) {
        movementRepository.deleteById(movementId);
    }

    public Optional<StockMovement> findById(long movementId) {
        return movementRepository.findById(movementId).map(movementMapper::toStockMovement);
    }

    public List<Long> create(List<StockMovement> movements) {
        return movements.stream().map(this::create).toList();
    }

}
