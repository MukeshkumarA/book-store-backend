package com.mk.bookstorebackend.service;

import com.mk.bookstorebackend.model.Cart;
import com.mk.bookstorebackend.model.CartItem;
import com.mk.bookstorebackend.repository.CartItemRepository;
import com.mk.bookstorebackend.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartServiceImplementation implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void addToCart(Long userId, CartItem cartItem) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return newCart;
        });

        cart.addItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateCartItem(Long userId, CartItem cartItem) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getBookId().equals(cartItem.getBookId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem item = existingCartItem.get();
            item.setQuantity(cartItem.getQuantity());
            item.setTotalPrice(cartItem.getQuantity() * cartItem.getPrice());
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("CartItem not found in cart");
        }
    }

    @Override
    public void removeFromCart(Long userId, Long bookId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        cart.getCartItems().removeIf(item -> item.getBookId().equals(bookId));
        cartRepository.save(cart);
    }

    @Override
    public List<CartItem> getCartItems(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        return cart.getCartItems();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
    }

    @Override
    public void checkoutCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        // Check out logic not implemented
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public Cart createCart(Long userId) {
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        newCart.setCartItems(new ArrayList<>());
        return cartRepository.save(newCart);
    }
}
