package com.cloudcart.customer.mapper;

import com.cloudcart.auth.dto.RegisterRequest;
import com.cloudcart.auth.dto.RegisterResponse;
import com.cloudcart.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toEntity(RegisterRequest request) {
        Customer customer = new Customer();

        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());

        // Password will be encoded in the service layer
        customer.setPasswordHash(request.password());

        return customer;
    }

    public RegisterResponse toRegisterResponse(Customer customer) {

        return new RegisterResponse(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getCreatedAt()
        );
    }
}
