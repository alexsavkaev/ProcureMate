package ru.aston.demo.warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.aston.demo.warehouse.dto.ProductsDto;
import ru.aston.demo.warehouse.dto.StockMovementDto;
import ru.aston.demo.warehouse.model.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductsMapper {
    ProductsDto toProductDto(Product product);
    Product toProducts(ProductsDto productsDto);
  //  Product toProduct(StockMovementDto stockMovementDto);
}
