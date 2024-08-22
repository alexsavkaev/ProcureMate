package ru.aston.demo.warehouse.dto;
import ru.aston.demo.warehouse.model.MovementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductsDto
        (
                Long productId,

                String productName,

                BigDecimal productPrice,

                String productInfo,

                int productQuantity,
                LocalDateTime movedAt,


                MovementType movementType

        ) {


}
