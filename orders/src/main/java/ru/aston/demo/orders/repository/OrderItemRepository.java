package ru.aston.demo.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.demo.orders.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
