package com.cloudcart.auth.dto;

import java.util.UUID;

public record LoginResponse(

        boolean success,
        String message,
        UUID customerId,
        String firstName,
        String lastName,
        String email
) {}