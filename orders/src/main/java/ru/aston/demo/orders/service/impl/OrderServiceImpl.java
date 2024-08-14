package ru.aston.demo.orders.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.entity.Order;
import ru.aston.demo.orders.mapper.OrderMapper;
import ru.aston.demo.orders.repository.OrderRepository;
import ru.aston.demo.orders.service.OrderService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  OrderMapper objectMapper = Mappers.getMapper(OrderMapper.class);

  public Page<OrderDto> getList(Pageable pageable) {
    return orderRepository.findAll(pageable)
        .map(objectMapper::mapToDto);
  }

  public OrderDto getOne(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Entity with id `%s` not found".formatted(id)));
    return objectMapper.mapToDto(order);
  }

  public List<Order> getMany(List<Long> ids) {
    return orderRepository.findAllById(ids);
  }

  public Order create(Order order) {
    return orderRepository.save(order);
  }

  public Order patch(Long id, JsonNode patchNode) throws IOException {
    Order order = orderRepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Entity with id `%s` not found".formatted(id)));

    objectMapper.patch(patchNode, order);

    return orderRepository.save(order);
  }

  public List<Long> patchMany(List<Long> ids, JsonNode patchNode) throws IOException {
    Collection<Order> orders = orderRepository.findAllById(ids);

    for (Order order : orders) {
      objectMapper.patch(patchNode, order);
    }

    List<Order> resultOrders = orderRepository.saveAll(orders);
    return resultOrders.stream()
        .map(Order::getId)
        .toList();
  }

  public Order delete(Long id) {
    Order order = orderRepository.findById(id).orElse(null);
    if (order != null) {
      orderRepository.delete(order);
    }
    return order;
  }

  public void deleteMany(List<Long> ids) {
    orderRepository.deleteAllById(ids);
  }
}
