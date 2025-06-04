-- Create system_users (with auto-incrementing id)
CREATE TABLE system_users (
    id SERIAL PRIMARY KEY,            -- Auto-incrementing ID
    username VARCHAR(255) NOT NULL,    -- Username
    password VARCHAR(255) NOT NULL,    -- Password
    role VARCHAR(50) NOT NULL          -- Role (Admin, Seller, etc.)
);

-- Create products table (with auto-incrementing id)
CREATE TABLE products (
    id SERIAL PRIMARY KEY,            -- Auto-incrementing ID
    name VARCHAR(255) NOT NULL,        -- Product name
    description TEXT,                 -- Product description
    stock INT NOT NULL,               -- Available stock
    price DECIMAL(10, 2) NOT NULL      -- Product price
);

-- Create orders table (with auto-incrementing id)
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,             -- Auto-incrementing ID
    customer_id INT NOT NULL,          -- Customer (user) who created the order
    seller_id INT,                     -- Seller (user) who is handling the order
    status VARCHAR(50) DEFAULT 'PENDING', -- Order status (Pending, Processing, etc.)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Order creation timestamp
    FOREIGN KEY (customer_id) REFERENCES system_users(id),
    FOREIGN KEY (seller_id) REFERENCES system_users(id)
);

-- Create order_items table (with auto-incrementing id)
CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,             -- Auto-incrementing ID
    order_id INT NOT NULL,             -- Associated order
    product_id INT NOT NULL,           -- Associated product
    quantity INT NOT NULL,             -- Quantity of the product in the order
    price DECIMAL(10, 2) NOT NULL,     -- Price of the product at the time of order
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
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