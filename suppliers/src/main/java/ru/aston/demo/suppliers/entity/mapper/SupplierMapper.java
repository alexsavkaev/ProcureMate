package ru.aston.demo.suppliers.entity.mapper;

import org.mapstruct.Mapper;
import ru.aston.demo.suppliers.dto.SupplierDto;
import ru.aston.demo.suppliers.entity.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDto supplierDto(Supplier supplier);
    Supplier supplier(SupplierDto supplierDto);
}
