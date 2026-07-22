package com.cloudcart.auth.service;

import com.cloudcart.auth.dto.*;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    AuthResult login(LoginRequest request);

    void logout();
}