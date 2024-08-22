package ru.aston.demo.accounting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.aston.demo.accounting.dto.StockMovementDto;
import ru.aston.demo.accounting.model.StockMovement;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface StockMovementDtoMapper {

    @Mapping(source = "movementType", target = "type")
    StockMovement toStockMovement(StockMovementDto stockMovementDto);

    @Mapping(source = "type", target = "movementType")
    StockMovementDto toStockMovementDto(StockMovement stockMovement);
}
