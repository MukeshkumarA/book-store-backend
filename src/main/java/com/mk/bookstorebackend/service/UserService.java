package com.mk.bookstorebackend.service;

import com.mk.bookstorebackend.model.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> findByEmail(String email);

    public User save(User user);

    public Optional<User> findById(Long id);

    public User updateUser(Long id, User user);

    public void deleteUser(Long id);

}
