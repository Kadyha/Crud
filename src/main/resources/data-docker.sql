-- Seed data for docker profile
-- Assumes Hibernate creates tables using SINGLE_TABLE inheritance for Person hierarchy

-- Addresses
INSERT INTO address (id, street, city, state, postal_code, country) VALUES
  (1, 'Av. Siempre Viva 742', 'Springfield', 'SP', '12345', 'USA'),
  (2, 'Calle Falsa 123', 'Shelbyville', 'SB', '54321', 'USA')
ON DUPLICATE KEY UPDATE street=VALUES(street);

-- Persons (single table with discriminator person_type)
-- Base Person
INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id) VALUES
  (1, 'PERSON', 'Juan', 'Pérez', 'juan.perez@example.com', '1234567890', 1)
ON DUPLICATE KEY UPDATE email=VALUES(email);

-- Student extends Person (same table) with extra columns student_number, average_mark
INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id, student_number, average_mark) VALUES
  (2, 'STUDENT', 'Ana', 'García', 'ana.garcia@example.com', '555111222', 2, 'S12345', 8.5)
ON DUPLICATE KEY UPDATE email=VALUES(email), student_number=VALUES(student_number), average_mark=VALUES(average_mark);

-- Professor extends Person (same table) with extra column salary
INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id, salary) VALUES
  (3, 'PROFESSOR', 'Carlos', 'López', 'carlos.lopez@example.com', '555222333', 1, 45000)
ON DUPLICATE KEY UPDATE email=VALUES(email), salary=VALUES(salary);

-- Extra students
INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id, student_number, average_mark) VALUES
  (4, 'STUDENT', 'María', 'Suárez', 'maria.suarez@example.com', '555333444', 1, 'S67890', 7.2),
  (5, 'STUDENT', 'Pedro', 'Gómez', 'pedro.gomez@example.com', '555444555', 2, 'S24680', 9.1)
ON DUPLICATE KEY UPDATE email=VALUES(email), student_number=VALUES(student_number), average_mark=VALUES(average_mark);

-- Extra professors
INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id, salary) VALUES
  (6, 'PROFESSOR', 'Laura', 'Martínez', 'laura.martinez@example.com', '555555666', 2, 52000),
  (7, 'PROFESSOR', 'Diego', 'Fernández', 'diego.fernandez@example.com', '555666777', 1, 61000)
ON DUPLICATE KEY UPDATE email=VALUES(email), salary=VALUES(salary);
