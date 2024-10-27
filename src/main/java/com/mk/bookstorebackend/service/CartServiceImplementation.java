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

        CartItem finalCartItem = cartItem;
        Optional<CartItem> existingCartItem = cart.getCartItems().stream().
                filter(item -> item.getBookId().equals(finalCartItem.getBookId())).
                findFirst();

        if(existingCartItem.isPresent()) {
            // If item exists, increment the quantity and update total price
            CartItem item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            item.setTotalPrice(item.getQuantity() * item.getPrice());
        }
        else {
            cartItem = cartItemRepository.save(cartItem);
            cart.addItem(cartItem);
        }
//        save the cart
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
            // Save the updated CartItem
            cartItemRepository.save(item);
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("CartItem not found in cart");
        }
    }

    @Override
    public void removeFromCart(Long userId, Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("BookId cannot be null");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        // Find the CartItem to remove
        Optional<CartItem> itemToRemove = cart.getCartItems().stream()
                .filter(item -> bookId.equals(item.getBookId())) // Reverse order for null safety
                .findFirst();

        if (itemToRemove.isPresent()) {
            cart.removeItem(itemToRemove.get()); // Use the removeItem method to maintain total price
            cartRepository.save(cart); // Save the updated cart
        } else {
            throw new RuntimeException("CartItem not found in cart for bookId: " + bookId);
        }
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
        cart.setTotalPrice(0);
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
