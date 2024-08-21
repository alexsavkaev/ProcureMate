package ru.aston.demo.accounting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aston.demo.accounting.type.MovementType;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "stock_movement", schema = "movements")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockMovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long productId;
    private String productName;
    private long quantity;
    private BigDecimal price;
    private Instant movedAt;

    @Enumerated(EnumType.STRING)
    private MovementType type;

}
