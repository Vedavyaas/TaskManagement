-- === Insert Users First ===
INSERT INTO user_entity (id, username, password, email, role, full_name, created_at)
VALUES
    (1, 'admin', 'admin123', 'admin@amdox.com', 'ADMIN', 'System Admin', CURRENT_TIMESTAMP()),
    (2, 'john', 'john123', 'john@amdox.com', 'USER', 'John Doe', CURRENT_TIMESTAMP()),
    (3, 'emma', 'emma123', 'emma@amdox.com', 'USER', 'Emma Watson', CURRENT_TIMESTAMP()),
    (4, 'liam', 'liam123', 'liam@amdox.com', 'USER', 'Liam Brown', CURRENT_TIMESTAMP());

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
