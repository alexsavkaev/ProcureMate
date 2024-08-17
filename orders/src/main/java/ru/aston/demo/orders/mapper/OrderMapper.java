package ru.aston.demo.orders.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import javax.xml.transform.Result;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.dto.OrderItemDto;
import ru.aston.demo.orders.dto.OrderToAccountingReportDto;
import ru.aston.demo.orders.entity.Order;
import ru.aston.demo.orders.entity.OrderItem;

@Mapper
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
    @Mapping(source = "productId.productName", target = "product")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "productId.productPrice", target = "productPrice")
    @Mapping(source = "productId.productId", target = "productId")

    OrderItemDto mapOrderItemToDto(OrderItem orderItem);

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    void patch(JsonNode patchNode, @MappingTarget Order target);



}

