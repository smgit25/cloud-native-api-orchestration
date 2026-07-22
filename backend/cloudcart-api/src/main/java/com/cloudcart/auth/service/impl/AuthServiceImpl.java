package com.cloudcart.auth.service.impl;

import com.cloudcart.auth.dto.*;
import com.cloudcart.auth.jwt.JwtService;
import com.cloudcart.customer.entity.Customer;
import com.cloudcart.customer.mapper.CustomerMapper;
import com.cloudcart.customer.repository.CustomerRepository;
import com.cloudcart.auth.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger log =
            LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        Customer customer = customerMapper.toEntity(request);

        customer.setPasswordHash(
               passwordEncoder.encode(request.password()));

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toRegisterResponse(savedCustomer);
    }

    @Override
    public AuthResult login(LoginRequest request) {

        Customer customer = customerRepository.findByEmail(request.email())
                .orElseThrow(()->new IllegalArgumentException("Invalid email or password"));

        if(!passwordEncoder.matches(request.password(), customer.getPasswordHash())){
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtService.generateToken(customer);
        log.info(token);
        ResponseCookie cookie = ResponseCookie.from(
                "auth-token",
                token)
                    .httpOnly(true)
                .secure(false)      // true when using HTTPS
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();
        return new AuthResult(token, customer);
    }

    @Override
    public void logout() {

//        authService.logout();
    }
}
