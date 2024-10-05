package com.mk.bookstorebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    @Column(nullable = false, columnDefinition = "float default 0.0")
    private double totalPrice;

    public void addItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
        recalculateTotalPrice();
    }

    public void removeItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null);
        recalculateTotalPrice();
    }

    public void recalculateTotalPrice() {
        totalPrice = cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}
