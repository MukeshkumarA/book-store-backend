package com.mk.bookstorebackend.service;

import com.mk.bookstorebackend.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public Order placeOrder(Long userId);

    public List<Order> getAllOrders();

    public Optional<Order> getOrderById(Long id);

    public Optional<List<Order>> getOrdersByUserId(Long id);
}
