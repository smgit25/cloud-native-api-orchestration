package com.cloudcart.customer.service;

import com.cloudcart.customer.dto.RegisterRequest;
import com.cloudcart.customer.dto.RegisterResponse;
import com.cloudcart.customer.entity.Customer;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);
}
