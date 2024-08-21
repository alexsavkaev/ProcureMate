package ru.aston.demo.accounting.mapper;

import org.mapstruct.Mapper;
import ru.aston.demo.accounting.dto.StockMovementDto;
import ru.aston.demo.accounting.model.StockMovement;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface StockMovementDtoMapper {

    StockMovement toStockMovement(StockMovementDto stockMovementDto);

    StockMovementDto toStockMovementDto(StockMovement stockMovement);
}
