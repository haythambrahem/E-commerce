package org.example.pfa.mapper;

import org.example.pfa.dto.OrderDTO;
import org.example.pfa.dto.OrderItemDTO;
import org.example.pfa.entity.Order;
import org.example.pfa.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        if (order == null) return null;

        return OrderDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .total(order.getTotal())
                .status(order.getStatus())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .userName(order.getUser() != null ? order.getUser().getUserName() : null)
                .orderItems(order.getOrderitemList() != null 
                        ? order.getOrderitemList().stream().map(this::toOrderItemDTO).toList() 
                        : Collections.emptyList())
                .build();
    }

    public OrderItemDTO toOrderItemDTO(OrderItem item) {
        if (item == null) return null;

        return OrderItemDTO.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .subtotal(item.getSubtotal())
                .orderId(item.getOrder() != null ? item.getOrder().getId() : null)
                .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                .productName(item.getProduct() != null ? item.getProduct().getName() : null)
                .productPrice(item.getProduct() != null ? item.getProduct().getPrice() : null)
                .build();
    }

    public List<OrderDTO> toDTOList(List<Order> orders) {
        if (orders == null) return Collections.emptyList();
        return orders.stream().map(this::toDTO).toList();
    }
}
