INSERT INTO Role VALUES
('Role_Admin', 'Admin'),
('Role_Agent', 'Agent');

INSERT INTO Account (email, username, full_name, password, locked, status) VALUES
('admin@gmail.com', 'admin', 'Ho Tuan Kiet','$2a$10$NAR4wfpHRZBZaQ5nFqKb6O3Rp9YmDSTNBqxk3HtRppvQBbSlqwpr2', false, true);

INSERT INTO account_role (username, role_id) VALUES
('admin', 'Role_Admin');

INSERT INTO Account (email, username, full_name ,password, locked, status) VALUES
('kiettyranno@gmail.com', 'kiettyranno', 'Ho Tuan Kiet','$2a$10$NAR4wfpHRZBZaQ5nFqKb6O3Rp9YmDSTNBqxk3HtRppvQBbSlqwpr2', false, true);

INSERT INTO account_role (username, role_id) VALUES
('kiettyranno', 'Role_Agent');