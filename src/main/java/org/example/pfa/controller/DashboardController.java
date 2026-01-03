package org.example.pfa.controller;

import org.example.pfa.repository.ProductRepository;
import org.example.pfa.repository.OrderRepo; // ✅ CORRECT
import org.example.pfa.repository.UserRepo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping; // ✅ Nécessaire pour @GetMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map; // ✅ Nécessaire pour Map

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final ProductRepository productRepo;
    private final OrderRepo orderRepo; // ✅ Utiliser le bon nom
    private final UserRepo userRepo;

    // ✅ Constructeur
    public DashboardController(ProductRepository productRepo,
                               OrderRepo orderRepo,
                               UserRepo userRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    // ✅ Endpoint pour récupérer les statistiques
    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("products", productRepo.count());
        stats.put("orders", orderRepo.count());
        stats.put("users", userRepo.count());
        return stats;
    }
}
