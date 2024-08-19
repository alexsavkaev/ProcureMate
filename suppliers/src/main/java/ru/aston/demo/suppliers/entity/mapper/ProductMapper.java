package ru.aston.demo.suppliers.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.aston.demo.suppliers.dto.ProductDto;
import ru.aston.demo.suppliers.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "supplierDto.id", source = "supplier.id")
    @Mapping(target = "supplierDto.supplierName", source = "supplier.supplierName")
    ProductDto toDto(Product entity);
    @Mapping(target = "supplier.supplierName", source = "supplierDto.supplierName")
    Product toEntity(ProductDto dto);
}
