package org.example.pfa.service;

import lombok.RequiredArgsConstructor;
import org.example.pfa.IService.IOrderItemService;
import org.example.pfa.dto.OrderItemDTO;
import org.example.pfa.entity.OrderItem;
import org.example.pfa.exception.ResourceNotFoundException;
import org.example.pfa.mapper.OrderMapper;
import org.example.pfa.repository.OrderItemRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {

    private final OrderItemRepo orderItemRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> getAllOrderItem() {
        return orderItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<OrderItemDTO> getAllOrderItemsPaged(Pageable pageable) {
        return orderItemRepository.findAll(pageable).map(orderMapper::toOrderItemDTO);
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", id));
    }

    @Override
    @Transactional
    public OrderItem UpdateOrderItem(Long id, OrderItem orderItemDetails) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", id));

        existingOrderItem.setQuantity(orderItemDetails.getQuantity());
        existingOrderItem.setSubtotal(orderItemDetails.getSubtotal());

        return orderItemRepository.save(existingOrderItem);
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("OrderItem", id);
        }
        orderItemRepository.deleteById(id);
    }
}
