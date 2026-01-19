package org.example.pfa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private int quantity;
    private BigDecimal subtotal;
    private Long orderId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
}
