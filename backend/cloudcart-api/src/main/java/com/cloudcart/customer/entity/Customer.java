package com.cloudcart.customer.entity;

import com.cloudcart.customer.enums
        .CustomerStatus;
import com.cloudcart.customer.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "customers",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_customer_email",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor

public class Customer {

    @Id
    @Column(name = "customer_id", nullable = false, updatable = false)
    private UUID customerId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CustomerStatus status;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    private void prePersist() {

        if (this.customerId == null) {
            this.customerId = UUID.randomUUID();
        }

        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }

        this.updatedAt = Instant.now();

        if (this.role == null) {
            this.role = Role.CUSTOMER;
        }

        if (this.status == null) {
            this.status = CustomerStatus.ACTIVE;
        }

        if (this.emailVerified == null) {
            this.emailVerified = Boolean.FALSE;
        }
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = Instant.now();
    }
}

