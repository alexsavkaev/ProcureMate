package ru.aston.demo.orders.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.entity.Order;
import ru.aston.demo.orders.exception.OrderCreationException;
import ru.aston.demo.orders.service.OrderService;
import ru.aston.demo.orders.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderResource {

    private final OrderService orderService;

    @GetMapping("/")
    public Page<OrderDto> getList(Pageable pageable) {
        return orderService.getList(pageable);
    }

    @GetMapping("/{id}")
    public OrderDto getOne(@PathVariable Long id) {
        return orderService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<Order> getMany(@RequestParam List<Long> ids) {
        return orderService.getMany(ids);
    }

    @PostMapping("/")
    public Order create(@RequestBody String json) throws OrderCreationException {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = null;
        try {
            order = objectMapper.readValue(json, Order.class);
        } catch (IOException e) {
            String errorMessage = "Failed to deserialize order: " + e.getMessage();
            log.error(errorMessage, e);
            throw new OrderCreationException(errorMessage, e);
        }
        return orderService.create(order);
    }

    @PatchMapping("/{id}")
    public Order patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        return orderService.patch(id, patchNode);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
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
