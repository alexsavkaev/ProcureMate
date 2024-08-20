package ru.aston.demo.orders.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.ToString.Exclude;
import ru.aston.demo.orders.entity.enums.Status;

import java.time.LocalDateTime;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "orders")
@Entity
@Builder
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    @JsonProperty("id")
    private Long id;

    @Column(name = "order_details")
    @JsonProperty("details")
    private String details;
    @JsonIgnore
    @Column(name = "creation_time", updatable = false, insertable = false)
    private LocalDateTime creationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonProperty("status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @Exclude
    @JsonProperty("supplier")
    private Supplier supplier;

    @OneToMany(mappedBy = "order")
    @Exclude
    @JsonProperty("orderItems")
    private List<OrderItem> orderItems;



}
