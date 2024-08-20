package ru.aston.demo.orders.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.aston.demo.orders.dto.CreatedOrderResponseDto;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.dto.OrderItemDto;
import ru.aston.demo.orders.dto.OrderToAccountingReportDto;
import ru.aston.demo.orders.dto.OrderToWarehouseReport;
import ru.aston.demo.orders.dto.ProductDto;
import ru.aston.demo.orders.dto.SupplierDto;
import ru.aston.demo.orders.entity.Order;
import ru.aston.demo.orders.entity.OrderItem;
import ru.aston.demo.orders.entity.Product;
import ru.aston.demo.orders.entity.Supplier;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  @Mapping(source = "id", target = "id")
  @Mapping(source = "details", target = "details")
  @Mapping(source = "creationTime", target = "creationTime")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "supplier", target = "supplier")
  @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "orderItemMapper")
  OrderDto mapToDto(Order order);

  @Named("orderItemMapper")
  @Mapping(source = "orderItemId", target = "id")
  @Mapping(source = "quantity", target = "quantity")
  @Mapping(source = "product.productPrice", target = "productPrice")
  @Mapping(source = "product.productId", target = "productId")
  OrderItemDto mapOrderItemToDto(OrderItem orderItem);

  @Mappings({
      @Mapping(target = "id", ignore = true)
  })
  void patch(JsonNode patchNode, @MappingTarget Order target);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "details", target = "details")
  @Mapping(source = "supplier", target = "supplier")
  @Mapping(source = "orderItems", target = "orderItems")
  CreatedOrderResponseDto toResponseDto(OrderDto orderDto);


  @Mappings({
      @Mapping(target = "orderId", source = "id"),
      @Mapping(target = "orderItems", source = "orderItems"),
      @Mapping(target = "movedAt", source = "creationTime")
  })
  OrderToWarehouseReport toOrderToWarehouseReport(Order order);


  @Mappings({
      @Mapping(target = "id", source = "id"),
      @Mapping(target = "movedAt", source = "creationTime"),
      @Mapping(target = "orderItems", source = "orderItems"),
      @Mapping(target = "type", constant = "INCOME")
  })
  OrderToAccountingReportDto toOrderDtoToAccountingReport(OrderDto order);

  @Mapping(target = "suppliers", source = "supplierDto")
  @Mapping(target = "productId", source = "id")
  @Mapping(target = "productPrice", source = "price")
  Product toProduct(ProductDto productDto);


  Supplier toSupplier(SupplierDto supplier);
  default List<Supplier> map(SupplierDto supplierDto) {
    return Collections.singletonList(toSupplier(supplierDto));
  }
}