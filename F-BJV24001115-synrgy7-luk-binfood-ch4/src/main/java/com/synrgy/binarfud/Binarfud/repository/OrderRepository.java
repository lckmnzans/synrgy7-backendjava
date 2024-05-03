package com.synrgy.binarfud.Binarfud.repository;

import com.synrgy.binarfud.Binarfud.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
