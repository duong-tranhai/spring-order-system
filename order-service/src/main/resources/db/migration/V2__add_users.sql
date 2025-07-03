INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_SELLER');
INSERT INTO roles (name) VALUES ('ROLE_CUSTOMER');

-- Insert Admin user with password 123
INSERT INTO system_users (username, password) VALUES ('admin', '$2a$10$eWUuFVfcU51cFz/Fo/S/p.VooS2mr3za/ZQVhmQ.7Q1iq2vFgbZ9e');
INSERT INTO system_users (username, password) VALUES ('seller', '$2a$10$eWUuFVfcU51cFz/Fo/S/p.VooS2mr3za/ZQVhmQ.7Q1iq2vFgbZ9e');

-- Assign ROLE_ADMIN to user with ID 1 (admin user)
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM system_users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));

-- Assign ROLE_SELLER to user with ID 2 (seller user)
INSERT INTO user_roles (user_id, role_id) VALUES ((SELECT id FROM system_users WHERE username = 'seller'), (SELECT id FROM roles WHERE name = 'ROLE_SELLER'));
