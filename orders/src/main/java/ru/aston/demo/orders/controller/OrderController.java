package ru.aston.demo.orders.controller;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aston.demo.orders.dto.CreateOrderDto;
import ru.aston.demo.orders.dto.CreatedOrderResponseDto;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.entity.Order;
import ru.aston.demo.orders.exception.OrderCreationException;
import ru.aston.demo.orders.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  public List<OrderDto> getList() {
    return orderService.getList();
  }

  @GetMapping("/{id}")
  public OrderDto getOne(@PathVariable Long id) {
    return orderService.getOne(id);
  }

  @GetMapping("/by-ids")
  public List<Order> getMany(@RequestParam List<Long> ids) {
    return orderService.getMany(ids);
  }

  @PostMapping
  public ResponseEntity<CreatedOrderResponseDto> create(@RequestBody CreateOrderDto request) throws OrderCreationException {
    return orderService.create(request);
  }

  @PatchMapping("/{id}")
  public Order patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
    return orderService.patch(id, patchNode);
  }

  @PatchMapping
  public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode)
      throws IOException {
    return orderService.patchMany(ids, patchNode);
  }

  @DeleteMapping("/{id}")
  public Order delete(@PathVariable Long id) {
    return orderService.delete(id);
  }

  @DeleteMapping
  public void deleteMany(@RequestParam List<Long> ids) {
    orderService.deleteMany(ids);
  }
}
