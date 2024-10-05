package com.mk.bookstorebackend.service;

import com.mk.bookstorebackend.model.Cart;
import com.mk.bookstorebackend.model.CartItem;

import java.util.List;

public interface CartService {
    public void addToCart(Long userId, CartItem cartItem);
    public void updateCartItem(Long userId, CartItem cartItem);
    public void removeFromCart(Long userId, Long bookId);
    public List<CartItem> getCartItems(Long userId);
    public Cart getCartByUserId(Long userId);
    public void checkoutCart(Long userId);
    public void clearCart(Long userId);
    public Cart createCart(Long id);
}
