INSERT INTO roles(id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO users(id, active, birthdate, creation_date, email, name, password, surname, username, role_id)
VALUES (1, 1, DATE('1990-03-04'), now(), 'carlos@gmail.com', 'Carlos', 'carlo', 'carlito', 'carlo', 1);