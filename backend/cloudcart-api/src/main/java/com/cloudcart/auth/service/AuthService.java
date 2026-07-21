package com.cloudcart.auth.service;

import com.cloudcart.auth.dto.RegisterRequest;
import com.cloudcart.auth.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);
}
