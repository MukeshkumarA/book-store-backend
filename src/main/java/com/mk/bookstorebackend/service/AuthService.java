package com.mk.bookstorebackend.service;

import com.mk.bookstorebackend.dto.AuthenticationRequest;
import com.mk.bookstorebackend.dto.AuthenticationResponse;
import com.mk.bookstorebackend.dto.RegisterRequest;
import com.mk.bookstorebackend.config.JwtService;
import com.mk.bookstorebackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();

        var savedUser = userService.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .userId(savedUser.getId())  // Including userId
                .role(savedUser.getRole())  // return the role
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //FirstStep
        //We need to validate our request (validate whether password & username is correct)
        //Verify whether user present in the database
        //Which AuthenticationProvider -> DaoAuthenticationProvider (Inject)
        //We need to authenticate using authenticationManager injecting this authenticationProvider
        //SecondStep
        //Verify whether userName and password is correct => UserNamePasswordAuthenticationToken
        //Verify whether user present in db
        //generateToken
        //Return the token
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userService.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .userId(user.getId())  // Including userId
                .role(user.getRole())
                .build();
    }
}
