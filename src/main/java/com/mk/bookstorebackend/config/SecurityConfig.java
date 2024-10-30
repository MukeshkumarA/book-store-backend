package com.mk.bookstorebackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.mk.bookstorebackend.model.Role.ADMIN;
import static com.mk.bookstorebackend.model.Role.MEMBER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(req ->
//                        req.requestMatchers("/bookstore/api/v1/auth/*")
//                                .permitAll()
//                                .requestMatchers(HttpMethod.GET, "/bookstore/api/v1/books/**")
//                                .permitAll()
//                                .requestMatchers(HttpMethod.POST, "/bookstore/api/v1/books/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT, "/bookstore/api/v1/books/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/bookstore/api/v1/books/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.GET, "/bookstore/api/v1/users/**")
//                                .hasAnyRole("ADMIN", "MEMBER")
//                                .requestMatchers(HttpMethod.PUT, "/bookstore/api/v1/users/**")
//                                .hasAnyRole("ADMIN", "MEMBER")
//                                .requestMatchers(HttpMethod.DELETE, "/bookstore/api/v1/users/**")
//                                .hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.POST, "/bookstore/api/v1/cart/**")
//                                .hasAnyRole("ADMIN", "MEMBER")
//                                .requestMatchers(HttpMethod.GET, "/bookstore/api/v1/cart/**")
//                                .hasAnyRole("ADMIN", "MEMBER")
//                                .requestMatchers(HttpMethod.PUT, "/bookstore/api/v1/cart/**")
//                                .hasAnyRole("ADMIN", "MEMBER")
//                                .requestMatchers(HttpMethod.DELETE, "/bookstore/api/v1/cart/**")
//                                .hasAnyRole("ADMIN", "MEMBER")
//                                .requestMatchers(HttpMethod.POST, "/bookstore/api/v1/orders/**")
//                                .hasAnyRole("ADMIN", "MEMBER")
//                                .requestMatchers(HttpMethod.GET, "/bookstore/api/v1/orders/**").hasRole("ADMIN")
////                               .requestMatchers("/test/v1/management/**").hasAnyRole(ADMIN.name(), MEMBER.name())
//                                .anyRequest()
//                                .authenticated())
//                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.anyRequest().permitAll()) // Allow all requests
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
