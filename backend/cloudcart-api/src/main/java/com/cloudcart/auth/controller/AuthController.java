package com.cloudcart.auth.controller;


import com.cloudcart.auth.dto.*;
import com.cloudcart.auth.service.impl.AuthServiceImpl;
import com.cloudcart.customer.entity.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

        try {
            RegisterResponse response = authService.register(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (IllegalArgumentException e) {

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errorResponse);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        try {
            AuthResult authResult = authService.login(request);
            ResponseCookie cookie = ResponseCookie.from(
                            "auth-token",
                            authResult.token())
                    .httpOnly(true)
                    .secure(false)      // true when using HTTPS
                    .path("/")
                    .maxAge(3600)
                    .sameSite("Strict")
                    .build();

            Customer customer = authResult.customer();
            LoginResponse response = new LoginResponse(
                    true,
                    "Login successful",
                    customer.getCustomerId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail()
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {

        authService.logout();

        ResponseCookie cookie = ResponseCookie.from("auth-token", "")
                .httpOnly(true)
                .secure(false)          // true in production
                .path("/")
                .sameSite("Strict")
                .maxAge(0)
                .build();

        LogoutResponse response = new LogoutResponse(
                true,
                "Logout successful"
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);

    }
}
