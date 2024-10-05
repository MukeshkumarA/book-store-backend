package com.mk.bookstorebackend.controller;

import com.mk.bookstorebackend.model.User;
import com.mk.bookstorebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bookstore/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findById(userId);
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }

    // Delete user account (optional)
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

}
