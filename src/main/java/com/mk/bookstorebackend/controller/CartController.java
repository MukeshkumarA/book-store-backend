package com.mk.bookstorebackend.controller;

import com.mk.bookstorebackend.model.Cart;
import com.mk.bookstorebackend.model.CartItem;
import com.mk.bookstorebackend.model.User;
import com.mk.bookstorebackend.service.CartService;
import com.mk.bookstorebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<Cart> createCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Long userId ) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Cart emptyCart =  cartService.createCart(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(emptyCart);
    }


    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<Void> addToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartItem cartItem) {
        User user = userService.findByEmail(userDetails.getUsername())
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
        cartService.addToCart(user.getId(), cartItem);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long id) {
        Cart cart = cartService.getCartByUserId(id);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/items")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<List<CartItem>> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<CartItem> cartItems = cartService.getCartItems(user.getId());
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<Void> updateCartItem(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartItem cartItem) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        cartService.updateCartItem(user.getId(), cartItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{bookId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<Void> removeFromCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long bookId) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        cartService.removeFromCart(user.getId(), bookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<String> checkoutCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        cartService.checkoutCart(user.getId());
        return ResponseEntity.ok("Checkout successful");
    }

    @DeleteMapping("/clearcart")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMEBER')")
    public ResponseEntity<String> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        cartService.clearCart(user.getId());
        return ResponseEntity.ok("Cart is cleared");
    }
}
