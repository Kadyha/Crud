-- Seed data for cloud/Railway profile (idempotent)
-- Mirrors docker seed but with neutral IDs to avoid conflicts; safe to run multiple times

-- Addresses
INSERT INTO address (id, street, city, state, postal_code, country) VALUES
  (101, 'Av. Siempre Viva 742', 'Springfield', 'SP', '12345', 'USA'),
  (102, 'Calle Falsa 123', 'Shelbyville', 'SB', '54321', 'USA')
ON DUPLICATE KEY UPDATE street=VALUES(street), city=VALUES(city), state=VALUES(state), postal_code=VALUES(postal_code), country=VALUES(country);

-- Persons (single table with discriminator person_type)
INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id) VALUES
  (101, 'PERSON', 'Juan', 'Pérez', 'juan.perez+cloud@example.com', '1234567890', 101)
ON DUPLICATE KEY UPDATE email=VALUES(email), phone=VALUES(phone), address_id=VALUES(address_id);

INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id, student_number, average_mark) VALUES
  (102, 'STUDENT', 'Ana', 'García', 'ana.garcia+cloud@example.com', '555111222', 102, 'CS12345', 8.5)
ON DUPLICATE KEY UPDATE email=VALUES(email), phone=VALUES(phone), address_id=VALUES(address_id), student_number=VALUES(student_number), average_mark=VALUES(average_mark);

INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id, salary) VALUES
  (103, 'PROFESSOR', 'Carlos', 'López', 'carlos.lopez+cloud@example.com', '555222333', 101, 45000)
ON DUPLICATE KEY UPDATE email=VALUES(email), phone=VALUES(phone), address_id=VALUES(address_id), salary=VALUES(salary);

-- Extra records
INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id, student_number, average_mark) VALUES
  (104, 'STUDENT', 'María', 'Suárez', 'maria.suarez+cloud@example.com', '555333444', 101, 'CS67890', 7.2),
  (105, 'STUDENT', 'Pedro', 'Gómez', 'pedro.gomez+cloud@example.com', '555444555', 102, 'CS24680', 9.1)
ON DUPLICATE KEY UPDATE email=VALUES(email), phone=VALUES(phone), address_id=VALUES(address_id), student_number=VALUES(student_number), average_mark=VALUES(average_mark);

INSERT INTO person (id, person_type, first_name, last_name, email, phone, address_id, salary) VALUES
  (106, 'PROFESSOR', 'Laura', 'Martínez', 'laura.martinez+cloud@example.com', '555555666', 102, 52000),
  (107, 'PROFESSOR', 'Diego', 'Fernández', 'diego.fernandez+cloud@example.com', '555666777', 101, 61000)
ON DUPLICATE KEY UPDATE email=VALUES(email), phone=VALUES(phone), address_id=VALUES(address_id), salary=VALUES(salary);
