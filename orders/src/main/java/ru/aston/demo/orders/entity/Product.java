package ru.aston.demo.orders.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @JsonProperty("productId")
    private Long productId;

//    @JsonProperty("name")
    @Column(name = "product_name")
    @JsonProperty("product")
    private String productName;

    @Column(name = "product_price")
    @JsonProperty("productPrice")
    private BigDecimal productPrice;

    @ManyToMany
    @Lazy
    @JoinTable(name = "supplier_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "productId")
    private List<OrderItem> orderItems;
//    @JsonCreator
//    public Product(String productName) {
//        this.productName = productName;
//    }
//
//    @JsonCreator
//    public Product(@JsonProperty("id") Long id, @JsonProperty("name") String productName, @JsonProperty("price") BigDecimal price, @JsonProperty("suppliers") List<Supplier> suppliers, @JsonProperty("orderItems") List<OrderItem> orderItems) {
//        this.id = id;
//        this.productName = productName;
//        this.price = price;
//        this.suppliers = suppliers;
//        this.orderItems = orderItems;
//    }
}
