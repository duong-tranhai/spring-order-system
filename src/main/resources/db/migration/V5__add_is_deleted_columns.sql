
-- Add columns to the 'system_users' table
ALTER TABLE system_users
    ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP;

-- Add columns to the 'orders' table
ALTER TABLE orders
    ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP;

-- Add columns to the 'order_items' table
ALTER TABLE order_items
    ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP;

-- Add columns to the 'products' table
ALTER TABLE products
    ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP;

-- Add columns to the 'categories' table
ALTER TABLE categories
    ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP;

-- Add columns to the 'suppliers' table
ALTER TABLE suppliers
    ADD COLUMN is_deleted BOOLEAN DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP;

-- This index enforces uniqueness on the 'name' column ONLY for rows that are NOT deleted.
CREATE UNIQUE INDEX unique_active_category_name
ON categories (name)
WHERE is_deleted = false;

CREATE UNIQUE INDEX unique_active_category_prefix
ON categories (prefix)
WHERE is_deleted = false;


-- Create indexes on the 'is_deleted' column for improved query performance
-- These indexes are crucial because most SELECT queries will include 'WHERE is_deleted = FALSE'.

--CREATE INDEX idx_system_users_is_deleted ON system_users (is_deleted);
--CREATE INDEX idx_orders_is_deleted ON orders (is_deleted);
--CREATE INDEX idx_order_items_is_deleted ON order_items (is_deleted);
--CREATE INDEX idx_products_is_deleted ON products (is_deleted);
--CREATE INDEX idx_categories_is_deleted ON categories (is_deleted);
--CREATE INDEX idx_suppliers_is_deleted ON suppliers (is_deleted);