INSERT INTO Role VALUES
('Role_Admin', 'Admin'),
('Role_Agent', 'Agent');

INSERT INTO Account (email, username, password, locked, status) VALUES
('admin@gmail.com', 'admin', '$2a$10$NAR4wfpHRZBZaQ5nFqKb6O3Rp9YmDSTNBqxk3HtRppvQBbSlqwpr2', false, true);

INSERT INTO account_role (username, role_id) VALUES
('admin', 'Role_Admin');

INSERT INTO Account (email, username, password, locked, status) VALUES
('kiettyranno@gmail.com', 'kiettyranno', '$2a$10$NAR4wfpHRZBZaQ5nFqKb6O3Rp9YmDSTNBqxk3HtRppvQBbSlqwpr2', false, true);

INSERT INTO account_role (username, role_id) VALUES
('kiettyranno', 'Role_Agent');