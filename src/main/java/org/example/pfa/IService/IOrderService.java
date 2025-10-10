package org.example.pfa.IService;

import org.example.pfa.entity.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);


}

