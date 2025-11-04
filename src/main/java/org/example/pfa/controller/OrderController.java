package org.example.pfa.controller;

import org.example.pfa.IService.IOrderService;
import org.example.pfa.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")

public class OrderController {

@Autowired
    private IOrderService orderService;

@PostMapping
    public Order createOrder(@RequestBody Order order){
    return orderService.createOrder(order);
}

@GetMapping
    public List<Order> getallOrders(){
    return orderService.getAllOrders();
}

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order){
        return orderService.updateOrder(id, order);
    }


@DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
    orderService.deleteOrder(id);
}
















}
