package com.mk.bookstorebackend.controller;

import com.mk.bookstorebackend.model.Order;
import com.mk.bookstorebackend.model.User;
import com.mk.bookstorebackend.service.BookService;
import com.mk.bookstorebackend.service.OrderService;
import com.mk.bookstorebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/placeOrder")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<Order> placeOrder(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ResponseEntity.ok(orderService.placeOrder(user.getId()));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Order> getOrderById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);;
        return orderOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/userid/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long id) {
        Optional<List<Order>> ordersOptional = orderService.getOrdersByUserId(id);
        return ordersOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(Collections.emptyList()));
    }

}
