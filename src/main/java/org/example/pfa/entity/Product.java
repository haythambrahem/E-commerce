package org.example.pfa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer stock;

    // ✅ Enum pour les saisons
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    // ✅ Image URL
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<OrderItem> orderitemList;

    public enum Category {
        PRINTEMPS,
        ÉTÉ,
        AUTOMNE,
        HIVER
    }
}
