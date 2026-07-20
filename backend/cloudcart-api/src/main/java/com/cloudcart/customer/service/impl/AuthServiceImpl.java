package com.cloudcart.customer.service.impl;

import com.cloudcart.customer.dto.RegisterRequest;
import com.cloudcart.customer.dto.RegisterResponse;
import com.cloudcart.customer.entity.Customer;
import com.cloudcart.customer.mapper.CustomerMapper;
import com.cloudcart.customer.repository.CustomerRepository;
import com.cloudcart.customer.service.AuthService;
import com.cloudcart.order.service.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log =
            LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
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
}
