package org.example.pfa.repository;

import org.example.pfa.entity.Order;
import org.example.pfa.entity.OrderItem;
import org.example.pfa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderAndProduct(Order order, Product product);
}
