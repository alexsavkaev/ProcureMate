package ru.aston.demo.suppliers.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pruducts")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pruduct_name")
    private String productName;
    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;
}
