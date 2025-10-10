package org.example.pfa.repository;

import org.example.pfa.entity.Order;
import org.example.pfa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
}
