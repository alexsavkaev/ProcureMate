package ru.aston.demo.accounting.mapper;

import org.mapstruct.Mapper;
import ru.aston.demo.accounting.entity.StockMovementEntity;
import ru.aston.demo.accounting.model.StockMovement;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface StockMovementMapper {

    StockMovement toStockMovement(StockMovementEntity stockMovementEntity);

    StockMovementEntity toStockMovementEntity(StockMovement stockMovement);
}
