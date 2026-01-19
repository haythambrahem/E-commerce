package org.example.pfa.service;

import lombok.RequiredArgsConstructor;
import org.example.pfa.IService.IOrderService;
import org.example.pfa.dto.OrderCreateDTO;
import org.example.pfa.dto.OrderDTO;
import org.example.pfa.dto.OrderItemCreateDTO;
import org.example.pfa.entity.Order;
import org.example.pfa.entity.OrderItem;
import org.example.pfa.entity.Product;
import org.example.pfa.entity.User;
import org.example.pfa.exception.InsufficientStockException;
import org.example.pfa.exception.ResourceNotFoundException;
import org.example.pfa.mapper.OrderMapper;
import org.example.pfa.repository.OrderRepo;
import org.example.pfa.repository.ProductRepository;
import org.example.pfa.repository.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepo orderRepository;
    private final UserRepo userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Creates a new order with all order items, validates stock availability,
     * and calculates totals automatically.
     */
    @Transactional
    public Order createOrderFromDTO(OrderCreateDTO dto) {
        // Validate user exists
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.getUserId()));

        Order order = new Order();
        order.setUser(user);
        order.setDate(new Date());
        order.setStatus("PENDING");

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // Process each item in the order
        for (OrderItemCreateDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemDTO.getProductId()));

            // Get available stock with null safety
            int availableStock = product.getStock() != null ? product.getStock() : 0;

            // Validate stock availability
            if (availableStock < itemDTO.getQuantity()) {
                throw new InsufficientStockException(
                        product.getName(),
                        itemDTO.getQuantity(),
                        availableStock
                );
            }

            // Calculate subtotal for this item
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setSubtotal(subtotal);
            orderItem.setOrder(order);

            orderItems.add(orderItem);
            total = total.add(subtotal);

            // Reduce stock
            product.setStock(availableStock - itemDTO.getQuantity());
            productRepository.save(product);
        }

        order.setOrderitemList(orderItems);
        order.setTotal(total);

        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> getAllOrdersPaged(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, Order orderDetails) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        existingOrder.setDate(orderDetails.getDate());
        existingOrder.setStatus(orderDetails.getStatus());

        return orderRepository.save(existingOrder);
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        existingOrder.setStatus(status);
        return orderRepository.save(existingOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        // Restore stock when order is deleted
        if (order.getOrderitemList() != null) {
            for (OrderItem item : order.getOrderitemList()) {
                Product product = item.getProduct();
                if (product != null) {
                    product.setStock((product.getStock() != null ? product.getStock() : 0) + item.getQuantity());
                    productRepository.save(product);
                }
            }
        }

        orderRepository.delete(order);
    }
}
