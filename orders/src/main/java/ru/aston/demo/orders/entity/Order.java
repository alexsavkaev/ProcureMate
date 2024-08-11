package ru.aston.demo.orders.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.context.annotation.Lazy;
import ru.aston.demo.orders.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "order_details")
    private String details;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @ToString.Exclude
    private Supplier supplier;

    @OneToMany(mappedBy = "order")
    private ArrayList<OrderItem> orderItems;


}
;
