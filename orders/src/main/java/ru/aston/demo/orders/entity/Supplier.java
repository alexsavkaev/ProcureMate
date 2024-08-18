package ru.aston.demo.orders.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "supplier_name")
    @JsonProperty("supplierName")
    private String supplierName;

    @ManyToMany
    @Lazy
    @JoinTable(name = "supplier_products",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @OneToMany(mappedBy = "supplier")
    private List<Order> orders;

}