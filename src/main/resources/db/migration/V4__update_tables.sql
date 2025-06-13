-- Add new columns to the system_users table
ALTER TABLE system_users
    ADD COLUMN email VARCHAR(255) UNIQUE,                        -- Email address (unique)
    ADD COLUMN first_name VARCHAR(255),                           -- User's first name
    ADD COLUMN last_name VARCHAR(255),                            -- User's last name
    ADD COLUMN is_active BOOLEAN DEFAULT TRUE,                    -- Account active status (default: true)

    -- Common fields from BaseEntity
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    -- Created timestamp
    ADD COLUMN created_by VARCHAR(255),                            -- Created by user
    ADD COLUMN updated_at TIMESTAMP,                               -- Updated timestamp
    ADD COLUMN updated_by VARCHAR(255);

-- Alter the orders table
ALTER TABLE orders
    ADD COLUMN order_date TIMESTAMP,          -- Date when the order was placed
    ADD COLUMN total_amount DECIMAL(10, 2),  -- Total amount for the order
    ADD COLUMN shipping_address TEXT,         -- Shipping address for the order
    ADD COLUMN payment_status VARCHAR(50),
    ADD COLUMN created_by VARCHAR(255),                           -- Created by user
    ADD COLUMN updated_at TIMESTAMP,                              -- Updated timestamp
    ADD COLUMN updated_by VARCHAR(255);                           -- Updated by user

-- Alter the order_items table
ALTER TABLE order_items
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- Created timestamp
    ADD COLUMN created_by VARCHAR(255),                           -- Created by user
    ADD COLUMN updated_at TIMESTAMP,                              -- Updated timestamp
    ADD COLUMN updated_by VARCHAR(255);                           -- Updated by user

-- Alter the products table
ALTER TABLE products
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Created timestamp
    ADD COLUMN created_by VARCHAR(255),                          -- Created by user
    ADD COLUMN updated_at TIMESTAMP,                             -- Last updated timestamp
    ADD COLUMN updated_by VARCHAR(255),                          -- Updated by user
    ADD COLUMN supplier_id BIGINT,                               -- Foreign key to suppliers table
    ADD CONSTRAINT fk_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE SET NULL;  -- Foreign key relationship with suppliers table

