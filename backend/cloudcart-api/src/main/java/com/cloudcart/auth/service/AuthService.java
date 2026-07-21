package com.cloudcart.auth.service;

import com.cloudcart.auth.dto.LoginRequest;
import com.cloudcart.auth.dto.LoginResponse;
import com.cloudcart.auth.dto.RegisterRequest;
import com.cloudcart.auth.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}