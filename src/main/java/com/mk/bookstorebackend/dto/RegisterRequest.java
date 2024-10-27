package com.mk.bookstorebackend.dto;

import com.mk.bookstorebackend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Optional<String> address = Optional.empty();
    private Optional<String> phoneNumber = Optional.empty();
    private Role role;
}
