package ru.aston.demo.accounting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.aston.demo.accounting.type.MovementType;

@Entity
@Table(name = "stock_movement", schema = "movements")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StockMovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long productId;
    private String productName;
    private long quantity;
    private BigDecimal price;
    private LocalDateTime movedAt;

    @Enumerated(EnumType.STRING)
    private MovementType type;

}
