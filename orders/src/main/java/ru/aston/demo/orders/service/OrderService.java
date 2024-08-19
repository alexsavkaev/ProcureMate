package ru.aston.demo.orders.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import ru.aston.demo.orders.dto.CreateOrderDto;
import ru.aston.demo.orders.dto.CreatedOrderResponseDto;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.entity.Order;

import java.io.IOException;
import java.util.List;
import ru.aston.demo.orders.exception.OrderCreationException;

public interface OrderService {
    OrderDto getOne(Long id);
    List<Order> getMany(List<Long> ids);
    ResponseEntity<CreatedOrderResponseDto> create(CreateOrderDto order) throws OrderCreationException;
    Order patch(Long id, JsonNode patchNode) throws IOException;
    List<Long> patchMany(List<Long> ids, JsonNode patchNode) throws IOException;
    Order delete(Long id);
    void deleteMany(List<Long> ids);

    List<OrderDto> getList();
}
