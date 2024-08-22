package ru.aston.demo.warehouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product { //продукты
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private BigDecimal productPrice;
    @Column(name = "product_info")
    private String productInfo;
    @Column(name = "product_quantity")
    private int productQuantity;
    @Column(name = "movedAt")
    private LocalDateTime movedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type")
    private MovementType movementType;



}
