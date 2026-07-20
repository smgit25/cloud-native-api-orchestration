package com.cloudcart.customer.dto;

import java.time.Instant;
import java.util.UUID;

public record RegisterResponse(

        UUID customerId,
        String firstName,
        String lastName,
        String email,
        Instant createdAt

) {}
