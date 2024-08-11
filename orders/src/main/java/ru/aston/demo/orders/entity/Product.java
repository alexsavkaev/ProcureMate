package ru.aston.demo.orders.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private BigDecimal price;

    @ManyToMany
    @Lazy
    @JoinTable(name = "supplier_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
