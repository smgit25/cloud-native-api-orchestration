CREATE TABLE order_items (

                             id BIGSERIAL PRIMARY KEY,

                             order_id UUID NOT NULL,

                             product_id UUID NOT NULL,

                             quantity INTEGER NOT NULL,

                             unit_price DECIMAL(12,2) NOT NULL,

                             CONSTRAINT fk_order_items_orders
                                 FOREIGN KEY (order_id)
                                     REFERENCES orders(id)
);