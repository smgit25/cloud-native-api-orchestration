package com.cloudcart.auth.dto;

import com.cloudcart.customer.entity.Customer;

public record AuthResult(

        String token,
        Customer customer
) {
}
