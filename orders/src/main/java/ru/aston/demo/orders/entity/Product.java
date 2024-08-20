package ru.aston.demo.orders.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
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
    @JoinTable(name = "supplier_products",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private List<Supplier> suppliers;

    @Override
    public String toString() {
        return "Product{" +
            "productId=" + productId +
            ", productName='" + productName + '\'' +
            ", productPrice=" + productPrice +
            ", suppliers=" + suppliers +
            '}';
    }
}
