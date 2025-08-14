CREATE DATABASE task_manager;
USE task_manager;

CREATE TABLE tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    priority ENUM('Low', 'Medium', 'High') DEFAULT 'Medium',
    due_date DATE,
    status ENUM('Pending', 'Completed') DEFAULT 'Pending'
);

INSERT INTO tasks (title, description, priority, due_date, status) VALUES
('Finish Resume', 'Complete resume for Cognizant hiring portal', 'High', '2025-08-20', 'Pending'),
('Java Practice', 'Complete OOP and JDBC revision', 'Medium', '2025-08-18', 'Pending');
