package ru.aston.demo.orders.service.impl;


import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.dto.OrderToAccountingReportDto;
import ru.aston.demo.orders.entity.Order;
import ru.aston.demo.orders.exception.OrderCreationException;
import ru.aston.demo.orders.mapper.OrderMapper;
import ru.aston.demo.orders.repository.OrderRepository;
import ru.aston.demo.orders.service.OrderService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private final RestClient accountingRestClient = RestClient.create();

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
@Transactional
  public Order create(Order order) throws OrderCreationException{
//  try {
//    ResponseEntity<Void> response = accountingRestClient.post()
//        .uri("http://accounting:8080")
//        .contentType(APPLICATION_JSON)
//        .body(objectMapper.mapToDto(order))
//        .retrieve()
//        .toBodilessEntity();
//    if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
//      throw new OrderCreationException("Failed to create order", response.getStatusCode());
//    }
    return orderRepository.save(order) ;
//  } catch (OrderCreationException e) {
//    log.error("Error creating order", e);
//    throw e;
//  }
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
