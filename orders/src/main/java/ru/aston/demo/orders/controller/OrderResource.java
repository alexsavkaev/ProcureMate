package ru.aston.demo.orders.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.aston.demo.orders.dto.OrderDto;
import ru.aston.demo.orders.entity.Order;
import ru.aston.demo.orders.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rest/admin-ui/orders")
@RequiredArgsConstructor
public class OrderResource {

    private final OrderServiceImpl orderServiceImpl;

    @GetMapping
    public Page<OrderDto> getList(Pageable pageable) {
        return orderServiceImpl.getList(pageable);
    }

    @GetMapping("/{id}")
    public OrderDto getOne(@PathVariable Long id) {
        return orderServiceImpl.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<Order> getMany(@RequestParam List<Long> ids) {
        return orderServiceImpl.getMany(ids);
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderServiceImpl.create(order);
    }

    @PatchMapping("/{id}")
    public Order patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        return orderServiceImpl.patch(id, patchNode);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
        return orderServiceImpl.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public Order delete(@PathVariable Long id) {
        return orderServiceImpl.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        orderServiceImpl.deleteMany(ids);
    }

}
