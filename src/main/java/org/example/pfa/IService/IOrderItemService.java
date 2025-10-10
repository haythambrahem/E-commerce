package org.example.pfa.IService;

import org.example.pfa.entity.OrderItem;

import java.util.List;

public interface IOrderItemService {

    OrderItem createOrderItem (OrderItem orderItem);

    List<OrderItem> getAllOrderItem ();

    OrderItem getOrderItemById(Long id);

    OrderItem UpdateOrderItem(Long id, OrderItem orderItem);
    void deleteOrderItem(Long id);
}
