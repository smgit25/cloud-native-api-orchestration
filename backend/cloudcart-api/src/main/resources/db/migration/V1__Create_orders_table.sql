CREATE TABLE orders (

                        id UUID PRIMARY KEY,

                        order_id UUID NOT NULL UNIQUE,

                        customer_id UUID NOT NULL,

                        status VARCHAR(50) NOT NULL,

                        created_at TIMESTAMP NOT NULL,

                        updated_at TIMESTAMP NOT NULL

);