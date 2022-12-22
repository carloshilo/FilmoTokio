insert into roles (name) values ('ADMIN'); -- 1
insert into roles (name) values ('USER'); -- 2

insert into users (active, birthdate, creation_date, email, name, password, surname, username, role_id) values (true, '1990-01-01 18:33:09.242000', now(), 'admin@gmail.com', 'Admin', '$2a$10$2jEcuJBvvY8iLphLQ6male9mDxp0XN9nFK/9jUacyUBDOMYn8gGka', 'Super', 'admin', 1);

insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score) values (41, false, './static/img/peliculas/Guardianes de la Galaxia Especial felices fiestas.jpg', 'Con la intención de que Peter tenga una Navidad inolvidable, Mantis y Drax viajan a la Tierra para conseguir el regalo perfecto.', 'Guardianes de la Galaxia: Especial felices fiestas', 2022, 1, 0);
insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score) values (134, false, './static/img/peliculas/Animales fantásticos Los crímenes de Grindelwald.jpg', 'Cumpliendo con su amenaza, el mago Gellert Grindelwald escapa de su custodia y empieza a reunir seguidores con el objetivo de alzar a los magos purasangre para reinar sobre todas las criaturas mágicas.', 'Animales fantásticos: Los crímenes de Grindelwald', 2018, 1, 0);
insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score) values (108, false, './static/img/peliculas/La_llamada.jpg', 'María y Susana, dos adolescentes rebeldes, pasan el verano en un campamento católico. Con la música como denominador común, la rebelión de los adolescentes y el orden eclesiástico chocarán, creando un himno a la libertad y al primer amor.', 'La llamada', 2017, 1, 0);

insert into people (name, surname, type) values ('James', 'Gunn', 'DIRECTOR'); -- 1
insert into people (name, surname, type) values ('James', 'Gunn', 'SCREENWRITER'); -- 2
insert into people (name, surname, type) values ('Jim', 'Starlin', 'SCREENWRITER'); -- 3
insert into people (name, surname, type) values ('Luke', 'Klein', 'ACTOR'); -- 4
insert into people (name, surname, type) values ('Sean', 'Gunn', 'ACTOR'); -- 5
insert into people (name, surname, type) values ('Michael', 'Rooker', 'ACTOR'); -- 6
insert into people (name, surname, type) values ('John', 'Murphy', 'MUSICIAN'); -- 7
insert into people (name, surname, type) values ('Henry', 'Braham', 'PHOTOGRAPHER'); -- 8

insert into people (name, surname, type) values ('Javier', 'Ambrossi', 'DIRECTOR'); -- 9
insert into people (name, surname, type) values ('Javier', 'Calvo', 'DIRECTOR'); -- 10
insert into people (name, surname, type) values ('Javier', 'Ambrossi', 'SCREENWRITER'); -- 11
insert into people (name, surname, type) values ('Javier', 'Calvo', 'SCREENWRITER'); -- 12
insert into people (name, surname, type) values ('Macarena', 'García', 'ACTOR'); -- 13
insert into people (name, surname, type) values ('Anna', 'Castillo', 'ACTOR'); -- 14
insert into people (name, surname, type) values ('Belén', 'Cuesta', 'ACTOR'); -- 15
insert into people (name, surname, type) values ('José Miguel', 'Conejo Torres', 'MUSICIAN'); -- 16
insert into people (name, surname, type) values ('Migue', 'Amoedo', 'PHOTOGRAPHER'); -- 17

insert into people (name, surname, type) values ('David', 'Yates', 'DIRECTOR'); -- 18
insert into people (name, surname, type) values ('J.K.', 'Rowling', 'SCREENWRITER'); -- 19
insert into people (name, surname, type) values ('Eddie', 'Redmayne', 'ACTOR'); -- 20
insert into people (name, surname, type) values ('Katherine', 'Waterston', 'ACTOR'); -- 21
insert into people (name, surname, type) values ('Dan', 'Fogler', 'ACTOR'); -- 22
insert into people (name, surname, type) values ('James Newton', 'Howard', 'MUSICIAN'); -- 23
insert into people (name, surname, type) values ('Philippe', 'Rousselot', 'PHOTOGRAPHER'); -- 24

insert into rel_person_film (film, person) values (1, 1);
insert into rel_person_film (film, person) values (1, 2);
insert into rel_person_film (film, person) values (1, 3);
insert into rel_person_film (film, person) values (1, 4);
insert into rel_person_film (film, person) values (1, 5);
insert into rel_person_film (film, person) values (1, 6);
insert into rel_person_film (film, person) values (1, 7);
insert into rel_person_film (film, person) values (1, 8);
insert into rel_person_film (film, person) values (2, 9);
insert into rel_person_film (film, person) values (2, 10);
insert into rel_person_film (film, person) values (2, 11);
insert into rel_person_film (film, person) values (2, 12);
insert into rel_person_film (film, person) values (2, 13);
insert into rel_person_film (film, person) values (2, 14);
insert into rel_person_film (film, person) values (2, 15);
insert into rel_person_film (film, person) values (2, 16);
insert into rel_person_film (film, person) values (2, 17);
insert into rel_person_film (film, person) values (3, 18);
insert into rel_person_film (film, person) values (3, 19);
insert into rel_person_film (film, person) values (3, 20);
insert into rel_person_film (film, person) values (3, 21);
insert into rel_person_film (film, person) values (3, 22);
insert into rel_person_film (film, person) values (3, 23);
insert into rel_person_film (film, person) values (3, 24);

