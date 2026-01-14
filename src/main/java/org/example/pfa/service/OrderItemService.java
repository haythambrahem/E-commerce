package org.example.pfa.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.pfa.IService.IOrderItemService;
import org.example.pfa.entity.Order;
import org.example.pfa.entity.OrderItem;
import org.example.pfa.entity.Product;
import org.example.pfa.repository.OrderItemRepo;
import org.example.pfa.repository.OrderRepo;
import org.example.pfa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderItemRepo orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepo orderRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        if (orderItem.getOrder() == null || orderItem.getOrder().getId() == null) {
            throw new RuntimeException("Order ID is required");
        }
        if (orderItem.getProduct() == null || orderItem.getProduct().getId() == null) {
            throw new RuntimeException("Product ID is required");
        }

        Order order = orderRepository.findById(orderItem.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(orderItem.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        orderItem.setOrder(order);
        orderItem.setProduct(product);

        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getAllOrderItem() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public OrderItem UpdateOrderItem(Long id, OrderItem orderItemDetails) {
        OrderItem existingOrderItem = orderItemRepository.findById(id).orElse(null);
        if (existingOrderItem != null) {
            existingOrderItem.setQuantity(orderItemDetails.getQuantity());
            existingOrderItem.setSubtotal(orderItemDetails.getSubtotal());
            existingOrderItem.setOrder(orderItemDetails.getOrder());
            existingOrderItem.setProduct(orderItemDetails.getProduct());
            return orderItemRepository.save(existingOrderItem);
        }
        return null;
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
