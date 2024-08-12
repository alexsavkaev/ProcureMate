package ru.aston.demo.accounting.mapper;

import ru.aston.demo.accounting.dto.OrderDto;
import ru.aston.demo.accounting.dto.OrderItemDto;
import ru.aston.demo.accounting.model.StockMovement;

import java.util.List;

public class OrderMovementMapper {
    public static List<StockMovement> toStockMovementDto(OrderDto orderDto) {

        List<OrderItemDto> list = orderDto.orderItems();

        return list.stream().map(orderItemDto -> new StockMovement(
                orderItemDto.id(),
                orderItemDto.productId(),
                orderItemDto.itemName(),
                orderItemDto.quantity(),
                orderItemDto.productPrice(),
                orderDto.movedAt(),
                orderDto.type())).toList();
    }
}
