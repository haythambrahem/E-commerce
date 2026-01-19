package org.example.pfa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Date date;
    private BigDecimal total;
    private String status;

    @NotNull(message = "User ID is required")
    private Long userId;
    private String userName;

    private List<OrderItemDTO> orderItems;
}
