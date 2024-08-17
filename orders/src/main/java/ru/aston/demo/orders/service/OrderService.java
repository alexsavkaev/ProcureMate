package ru.aston.demo.orders.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.entity.Order;

import java.io.IOException;
import java.util.List;
import ru.aston.demo.orders.exception.OrderCreationException;

public interface OrderService {
    Page<OrderDto> getList(Pageable pageable);
    OrderDto getOne(Long id);
    List<Order> getMany(List<Long> ids);
    Order create(Order order) throws OrderCreationException;
    Order patch(Long id, JsonNode patchNode) throws IOException;
    List<Long> patchMany(List<Long> ids, JsonNode patchNode) throws IOException;
    Order delete(Long id);
    void deleteMany(List<Long> ids);
}
