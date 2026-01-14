package org.example.pfa.controller;

import org.example.pfa.IService.IOrderService;
import org.example.pfa.entity.Order;
import org.example.pfa.entity.User;
import org.example.pfa.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private UserRepo userRepository;

    // ✅ Créer une commande liée à l'utilisateur connecté
    @PostMapping
    public Order createOrder(@RequestBody Order order, User user) { Long userId = user.getId(); if (user == null) { throw new RuntimeException("User not found"); } return orderService.createOrder(order, userId); }
    // ✅ Récupérer les commandes de l'utilisateur connecté
    @GetMapping("/my")
    public List<Order> getMyOrders(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return orderService.getOrdersByUser(user);
    }

    // ✅ Update commande
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    // ✅ Delete commande
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
