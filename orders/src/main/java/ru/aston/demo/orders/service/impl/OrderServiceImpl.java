package ru.aston.demo.orders.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import ru.aston.demo.orders.config.AccountingProperties;
import ru.aston.demo.orders.config.SuppliersProperties;
import ru.aston.demo.orders.config.WarehouseProperties;
import ru.aston.demo.orders.dto.CreateOrderDto;
import ru.aston.demo.orders.dto.CreatedOrderResponseDto;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.entity.Order;
import ru.aston.demo.orders.entity.OrderItem;
import ru.aston.demo.orders.entity.enums.Status;
import ru.aston.demo.orders.mapper.OrderMapper;
import ru.aston.demo.orders.repository.OrderItemRepository;
import ru.aston.demo.orders.repository.OrderRepository;
import ru.aston.demo.orders.repository.ProductRepository;
import ru.aston.demo.orders.repository.SupplierRepository;
import ru.aston.demo.orders.service.OrderService;

import java.util.List;
import ru.aston.demo.orders.util.RestClientFactory;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  //  private final RestClientFactory clientFactory;
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final SupplierRepository supplierRepository;
  private final OrderItemRepository orderItemRepository;
  private final ProductRepository productRepository;
  private final WarehouseProperties warehouseProperties;
  private final SuppliersProperties suppliersProperties;
  private final AccountingProperties accountingProperties;


  @Override
  public OrderDto getOne(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Entity with id `%s` not found".formatted(id)));
    return orderMapper.mapToDto(order);
  }

  @Override
  public List<Order> getMany(List<Long> ids) {
    return orderRepository.findAllById(ids);
  }

  @Override
  @Transactional
  public ResponseEntity<CreatedOrderResponseDto> create(CreateOrderDto request) {
    try {
      Order order = Order.builder()
          .details(request.details())
          .status(Status.NEW)
          .supplier(supplierRepository.findById(request.supplierId()).orElseThrow())
          .build();

      Order savedOrder = orderRepository.save(order);

      List<OrderItem> orderItems = request.orderItems().stream()
          .map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.quantity());
            orderItem.setProduct(productRepository.findById(item.productId()).orElseThrow());
            orderItem.setOrder(savedOrder);
            return orderItem;
          })
          .collect(Collectors.toList());

      orderItemRepository.saveAll(orderItems);
      savedOrder.setOrderItems(orderItems);

      return ResponseEntity.status(HttpStatus.CREATED)
          .body(orderMapper.toResponseDto(orderMapper.mapToDto(savedOrder)));
    } catch (Exception e) {
      if (e instanceof NoSuchElementException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    }
  }

  @Override
  public Order patch(Long id, JsonNode patchNode) {
    Order order = orderRepository.findById(id).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Entity with id `%s` not found".formatted(id)));

    orderMapper.patch(patchNode, order);

    return orderRepository.save(order);
  }

  @Override
  public List<Long> patchMany(List<Long> ids, JsonNode patchNode) {
    Collection<Order> orders = orderRepository.findAllById(ids);

    for (Order order : orders) {
      orderMapper.patch(patchNode, order);
    }

    List<Order> resultOrders = orderRepository.saveAll(orders);
    return resultOrders.stream()
        .map(Order::getId)
        .toList();
  }

  @Override
  public Order delete(Long id) {
    Order order = orderRepository.findById(id).orElse(null);
    if (order != null) {
      orderRepository.delete(order);
    }
    return order;
  }

  @Override
  public void deleteMany(List<Long> ids) {
    orderRepository.deleteAllById(ids);
  }

  @Override
  public List<OrderDto> getList() {
    return orderRepository.findAll().stream()
        .map(orderMapper::mapToDto)
        .toList();
  }

  private void sendReportToWarehouse(String url, Order order) {
    RestClient restClient = RestClientFactory.getRestClient(url);
    restClient.post()
        .contentType(MediaType.APPLICATION_JSON)
        .body(orderMapper.toOrderToWarehouseReport(order))
        .retrieve()
        .toBodilessEntity();
  }

  private void sendReportToAccounting(String url, Order order) {
    RestClient restClient = RestClientFactory.getRestClient(url);
    restClient.post()
        .contentType(MediaType.APPLICATION_JSON)
        .body(orderMapper.toOrderDtoToAccountingReport(orderMapper.mapToDto(order)))
        .retrieve()
        .toBodilessEntity();
  }
}