package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Transactional
    @Override
    public Order insertOrder(Order order) {
        order = orderRepository.save(order);
        log.info("Order Data with id= "+order.getId()+" successfully created");
        return order;
    }

    @Override
    public Order getOrder(String orderId) {
        UUID uuid = UUID.fromString(orderId);
        Optional<Order> order = orderRepository.findById(uuid);
        if (order.isEmpty()) throw new RuntimeException();
        return order.get();
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty()) return Collections.emptyList();
        return orderList;
    }

    @Override
    public List<Order> getAllOrdersByUser(Users user) {
        List<Order> orderList = orderRepository.findAllByUser(user);
        if (orderList.isEmpty()) return Collections.emptyList();
        return orderList;
    }

    @Override
    public List<Order> getAllOrdersInBetween(Date startDate, Date endDate) {
        return orderRepository.findByOrderTimeBetween(startDate, endDate);
    }

    @Override
    public Order updateOrderStatus(Order order) {
        orderRepository.save(order);
        return order;
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }
}
