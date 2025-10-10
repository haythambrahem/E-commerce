package org.example.pfa.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.pfa.IService.IOrderItemService;
import org.example.pfa.entity.OrderItem;
import org.example.pfa.repository.OrderItemRepo;
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

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
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
    public OrderItem UpdateOrderItem (Long id, OrderItem orderItemDetails) {
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
