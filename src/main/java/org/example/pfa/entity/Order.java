package org.example.pfa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private BigDecimal total;
    private String status;


    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL)
    private List<OrderItem> orderitemList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
