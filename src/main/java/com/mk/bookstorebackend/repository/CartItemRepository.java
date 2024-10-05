package com.mk.bookstorebackend.repository;

import com.mk.bookstorebackend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
