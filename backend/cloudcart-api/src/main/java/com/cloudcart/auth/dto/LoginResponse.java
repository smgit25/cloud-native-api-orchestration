package com.cloudcart.auth.dto;

import java.util.UUID;

public record LoginResponse(
        UUID customerId,
        String firstName,
        String lastName,
        String email,
        String token
) {}