package org.example.pfa.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.pfa.IService.IOrderService;
import org.example.pfa.entity.Order;
import org.example.pfa.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, Order orderDetails) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setDate(orderDetails.getDate());
            existingOrder.setStatus(orderDetails.getStatus());
            existingOrder.setUser(orderDetails.getUser());
            existingOrder.setOrderitemList(orderDetails.getOrderitemList());
            return orderRepository.save(existingOrder);
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
