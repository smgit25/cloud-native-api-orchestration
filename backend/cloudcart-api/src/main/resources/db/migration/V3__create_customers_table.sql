--for H2--
CREATE TABLE customers (
                           customer_id UUID PRIMARY KEY,
                           first_name VARCHAR(100) NOT NULL,
                           last_name VARCHAR(100) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           password_hash VARCHAR(255) NOT NULL,
                           role VARCHAR(20) NOT NULL,
                           status VARCHAR(20) NOT NULL,
                           email_verified BOOLEAN NOT NULL,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL
);