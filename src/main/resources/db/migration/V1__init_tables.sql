-- Create roles table to store role information (e.g., 'ROLE_ADMIN', 'ROLE_SELLER', etc.)
CREATE TABLE roles (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,               -- Auto-incrementing ID
    name VARCHAR(50) NOT NULL UNIQUE      -- Role name (e.g., 'ROLE_ADMIN', 'ROLE_SELLER')
);

-- Create system_users table
CREATE TABLE system_users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,                -- Auto-incrementing ID
    username VARCHAR(255) NOT NULL,        -- Username
    password VARCHAR(255) NOT NULL         -- Password
);

-- Create a join table to store the many-to-many relationship between system_users and roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,              -- Foreign key for system_users
    role_id BIGINT NOT NULL,              -- Foreign key for roles
    PRIMARY KEY (user_id, role_id),       -- Composite primary key for the join table
    FOREIGN KEY (user_id) REFERENCES system_users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Create products table (with auto-incrementing id)
CREATE TABLE products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,                -- Auto-incrementing ID
    name VARCHAR(255) NOT NULL,            -- Product name
    description TEXT,                     -- Product description
    stock INT NOT NULL,                   -- Available stock
    price DECIMAL(10, 2) NOT NULL          -- Product price
);

-- Create orders table (with auto-incrementing id)
CREATE TABLE orders (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,                -- Auto-incrementing ID
    customer_id BIGINT NOT NULL,             -- Customer (user) who created the order
    seller_id BIGINT,                        -- Seller (user) who is handling the order
    status VARCHAR(50) DEFAULT 'PENDING', -- Order status (Pending, Processing, etc.)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Order creation timestamp
    FOREIGN KEY (customer_id) REFERENCES system_users(id),
    FOREIGN KEY (seller_id) REFERENCES system_users(id)
);

-- Create order_items table (with auto-incrementing id)
CREATE TABLE order_items (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,                -- Auto-incrementing ID
    order_id BIGINT NOT NULL,                -- Associated order
    product_id BIGINT NOT NULL,              -- Associated product
    quantity INT NOT NULL,                -- Quantity of the product in the order
    price DECIMAL(10, 2) NOT NULL,        -- Price of the product at the time of order
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    UNIQUE (order_id, product_id)
);

-- Create flyway_schema_history table for Flyway migrations
CREATE TABLE IF NOT EXISTS flyway_schema_history (
    installed_rank INT NOT NULL,
    version VARCHAR(50),
    description VARCHAR(200),
    type VARCHAR(20) NOT NULL,
    script VARCHAR(1000) NOT NULL,
    checksum INT,
    installed_by VARCHAR(100) NOT NULL,
    installed_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    execution_time INT NOT NULL,
    success BOOLEAN NOT NULL,
    PRIMARY KEY (installed_rank)
);
