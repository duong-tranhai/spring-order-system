-- Insert Admin user with password 123
INSERT INTO system_users (username, password, role)
VALUES
    ('admin', '$2a$10$eWUuFVfcU51cFz/Fo/S/p.VooS2mr3za/ZQVhmQ.7Q1iq2vFgbZ9e', 'ADMIN');

-- Insert Seller user with password 123
INSERT INTO system_users (username, password, role)
VALUES
    ('seller', '$2a$10$eWUuFVfcU51cFz/Fo/S/p.VooS2mr3za/ZQVhmQ.7Q1iq2vFgbZ9e', 'SELLER');
