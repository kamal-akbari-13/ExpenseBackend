-- Admin User Setup Script for PostgreSQL/Supabase
-- Run this script in your database to create an admin user

-- First, make sure the roles exist
INSERT INTO role (role_id, name) 
VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

-- Create admin user (password: admin123 - BCrypt encoded)
-- Note: The password below is BCrypt encoded version of "admin123"
INSERT INTO users (id, username, email, password, enabled, verification_code, verification_code_expiry_time, profile_img_url)
VALUES (
    nextval('users_id_seq'), -- or use a specific ID if sequence doesn't exist
    'admin',
    'admin@expensetracker.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', -- admin123 encoded
    true,
    'ADMIN_VERIFIED',
    EXTRACT(EPOCH FROM NOW() + INTERVAL '24 hours') * 1000,
    null
)
ON CONFLICT (email) DO NOTHING;

-- Assign admin role to the admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.role_id
FROM users u, role r
WHERE u.email = 'admin@expensetracker.com' 
AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

-- Verify the admin user was created
SELECT 
    u.id,
    u.username,
    u.email,
    u.enabled,
    array_agg(r.name) as roles
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN role r ON ur.role_id = r.role_id
WHERE u.email = 'admin@expensetracker.com'
GROUP BY u.id, u.username, u.email, u.enabled; 