-- === Insert Users First ===
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (1, 'admin', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'admin@amdox.com', 'ADMIN', 'System Admin', 'AMDox', 'IT', 'AMDox Corp', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (2, 'john', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'john@amdox.com', 'USER', 'John Doe', 'Engineering', 'Software', 'Tech Solutions Inc', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (3, 'emma', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'emma@amdox.com', 'USER', 'Emma Watson', 'Marketing', 'Digital', 'Global Marketing Ltd', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (4, 'liam', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'liam@amdox.com', 'USER', 'Liam Brown', 'HR', 'Operations', 'People First Corp', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (5, 'sophia', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'sophia@amdox.com', 'USER', 'Sophia Garcia', 'Finance', 'Accounting', 'Finance Experts LLC', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (6, 'noah', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'noah@amdox.com', 'USER', 'Noah Miller', 'Sales', 'Business', 'Sales Pro Inc', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (7, 'olivia', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'olivia@amdox.com', 'USER', 'Olivia Davis', 'Support', 'Customer', 'Help Desk Co', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (8, 'mason', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'mason@amdox.com', 'USER', 'Mason Wilson', 'R&D', 'Research', 'Innovate Labs', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (9, 'ava', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'ava@amdox.com', 'USER', 'Ava Martinez', 'Legal', 'Compliance', 'Legal Advisors Inc', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (10, 'ethan', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'ethan@amdox.com', 'USER', 'Ethan Anderson', 'Operations', 'Logistics', 'Ops Masters Ltd', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (11, 'admin2', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'admin2@amdox.com', 'ADMIN', 'Admin Two', 'AMDox', 'IT', 'AMDox Corp', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (12, 'admin3', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'admin3@amdox.com', 'ADMIN', 'Admin Three', 'AMDox', 'IT', 'AMDox Corp', CURRENT_TIMESTAMP());
INSERT INTO user_entity (id, username, password, email, role, full_name, organization, domain, company_name, created_at) VALUES (13, 'admin4', '$2a$10$ZsmpxUN3qGjWI2oDEhvrHOnBaF9kM4QK1WeoUxXuWVwjYKyHoPSMi', 'admin4@amdox.com', 'ADMIN', 'Admin Four', 'AMDox', 'IT', 'AMDox Corp', CURRENT_TIMESTAMP());
ALTER SEQUENCE USER_ENTITY_SEQ RESTART WITH 14;
-- username+123 is the password(hashed)
-- === Then Insert Tasks ===
INSERT INTO task_entity (id, title, description, status, created_at, updated_at, completed_at, assigned_user_id, created_by_id)
VALUES
    (1,  'Setup Server', 'Initialize Spring Boot and configure H2 database', 'IN_PROGRESS', CURRENT_TIMESTAMP(), NULL, NULL, 2, 1),
    (2,  'Create User Module', 'Design and implement user management features', 'PENDING', CURRENT_TIMESTAMP(), NULL, NULL, 2, 1),
    (3,  'System Check', 'Verify that startup SQL executes correctly', 'COMPLETED', CURRENT_TIMESTAMP(), NULL, CURRENT_TIMESTAMP(), 1, 1),
    (4,  'Build Authentication', 'Implement login and registration flow using Spring Security', 'IN_PROGRESS', CURRENT_TIMESTAMP(), NULL, NULL, 3, 1),
    (5,  'Design Database Schema', 'Define ER model for tasks and users', 'COMPLETED', CURRENT_TIMESTAMP(), NULL, CURRENT_TIMESTAMP(), 1, 1),
    (6,  'Integrate Email Service', 'Setup email notifications for task assignment', 'PENDING', CURRENT_TIMESTAMP(), NULL, NULL, 2, 1),
    (7,  'Add Role Management', 'Create roles ADMIN and USER with restricted access', 'IN_PROGRESS', CURRENT_TIMESTAMP(), NULL, NULL, 3, 1),
    (8,  'Frontend Dashboard', 'Develop task list dashboard using React', 'PENDING', CURRENT_TIMESTAMP(), NULL, NULL, 4, 1),
    (9,  'Fix UI Bugs', 'Resolve display issues in the task view page', 'COMPLETED', CURRENT_TIMESTAMP(), NULL, CURRENT_TIMESTAMP(), 4, 2),
    (10, 'Test REST APIs', 'Write JUnit tests for TaskController endpoints', 'IN_PROGRESS', CURRENT_TIMESTAMP(), NULL, NULL, 2, 1),
    (11, 'Optimize Queries', 'Improve performance of complex database joins', 'PENDING', CURRENT_TIMESTAMP(), NULL, NULL, 3, 1),
    (12, 'Implement Logging', 'Add centralized logging for API requests', 'IN_PROGRESS', CURRENT_TIMESTAMP(), NULL, NULL, 1, 1),
    (13, 'Dockerize App', 'Add Dockerfile and docker-compose for deployment', 'COMPLETED', CURRENT_TIMESTAMP(), NULL, CURRENT_TIMESTAMP(), 2, 1),
    (14, 'CI/CD Pipeline', 'Integrate GitHub Actions for build and test automation', 'PENDING', CURRENT_TIMESTAMP(), NULL, NULL, 4, 1),
    (15, 'Update Documentation', 'Write API documentation using Swagger', 'IN_PROGRESS', CURRENT_TIMESTAMP(), NULL, NULL, 3, 2),
    (16, 'Code Review', 'Review pull requests for feature branches', 'COMPLETED', CURRENT_TIMESTAMP(), NULL, CURRENT_TIMESTAMP(), 1, 2),
    (17, 'Add Pagination', 'Add pagination to task list endpoint', 'PENDING', CURRENT_TIMESTAMP(), NULL, NULL, 2, 1),
    (18, 'Dark Mode Support', 'Implement dark theme toggle in frontend', 'IN_PROGRESS', CURRENT_TIMESTAMP(), NULL, NULL, 4, 1),
    (19, 'Backup Strategy', 'Setup daily automatic database backup', 'PENDING', CURRENT_TIMESTAMP(), NULL, NULL, 3, 1),
    (20, 'Final Deployment', 'Deploy the application to production environment', 'PENDING', CURRENT_TIMESTAMP(), NULL, NULL, 1, 1);
