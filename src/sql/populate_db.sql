INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY) VALUES
('John Doe', '1990-01-15', 'Senior', 6000),
('Jane Smith', '1985-05-30', 'Middle', 4500),
('Sam Brown', '1995-10-20', 'Junior', 2800),
('Nancy Johnson', '2000-03-10', 'Trainee', 900),
('Alice Green', '1992-08-25', 'Middle', 4200),
('Bob White', '1988-12-05', 'Senior', 5200),
('Charlie Black', '1999-07-15', 'Junior', 3200),
('Eva Adams', '1982-11-11', 'Senior', 7000),
('Liam Wilson', '1997-04-22', 'Trainee', 850),
('Olivia Taylor', '1994-02-18', 'Middle', 4800);

INSERT INTO client (NAME) VALUES
('Acme Corp.'),
('Tech Innovators'),
('Global Solutions'),
('FutureTech'),
('Creative Minds');

INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE) VALUES
(1, '2022-01-01', '2023-01-01'),
(2, '2022-02-01', '2022-07-01'),
(3, '2021-03-15', '2023-03-15'),
(4, '2023-05-01', '2023-11-01'),
(5, '2022-06-01', '2023-06-01'),
(1, '2021-01-01', '2022-01-01'),
(2, '2023-02-01', '2024-02-01'),
(3, '2021-05-01', '2022-05-01'),
(4, '2023-07-01', '2023-12-01'),
(5, '2022-09-01', '2023-09-01');

INSERT INTO project_worker (PROJECT_ID, WORKER_ID) VALUES
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5),
(3, 6), (3, 7), (3, 8),
(4, 9),
(5, 10), (5, 1),
(6, 2), (6, 3), (6, 4),
(7, 5), (7, 6),
(8, 7),
(9, 8), (9, 9),
(10, 10), (10, 1), (10, 2);