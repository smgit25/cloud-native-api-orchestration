package com.cloudcart.customer.repository;

import com.cloudcart.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByEmail(String email);

    //Optional<Customer> findByE(String email)

    boolean existsByEmail(String email);
}

