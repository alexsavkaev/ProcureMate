package ru.aston.demo.orders.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_Id")
    @JsonProperty("orderItemId")
    private Long orderItemId;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    private int quantity;


    @ManyToOne
    @JoinColumn( name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonProperty("product")
    private Product product;


}
