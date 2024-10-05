package com.mk.bookstorebackend.service;

import com.mk.bookstorebackend.model.*;
import com.mk.bookstorebackend.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements  OrderService{

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserService userService;

    @Override
    @Transactional
    public Order placeOrder(Long userId) {
        // Retrieve user and cart
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Cart cart = cartService.getCartByUserId(userId);

        // Validate cart
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // Map cart items to order items
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(this::convertToOrderItem)
                .toList();

        // Calculate total price
        double totalPrice = orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum();

        // Create order entity
        Order order = Order.builder()
                .orderDate(new Date())
                .userId(userId)
                .totalAmount(totalPrice)
                .shippingAddress(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();

        // Add order items to order
        orderItems.forEach(order::addItem);

        // Save order and clear cart
        order = orderRepository.save(order);
        cartService.clearCart(userId);

        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<List<Order>> getOrdersByUserId(Long id) {
        return orderRepository.findAllByUserId(id);
    }


    private OrderItem convertToOrderItem(CartItem cartItem) {
        return OrderItem.builder()
                .bookId(cartItem.getBookId())
                .title(cartItem.getTitle())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .totalPrice(cartItem.getTotalPrice())
                .build();
    }
}
