package ru.aston.demo.suppliers.entity.mapper;

import org.mapstruct.Mapper;
import ru.aston.demo.suppliers.dto.ProductDto;
import ru.aston.demo.suppliers.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product entity);
    Product toEntity(ProductDto dto);
}
