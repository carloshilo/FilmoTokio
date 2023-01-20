insert into roles (name) values ('ADMIN'); -- 1
insert into roles (name) values ('USER'); -- 2

insert into users (active, birthdate, creation_date, email, name, password, surname, username, role_id) values (true, '1990-01-01 18:33:09.242000', now(), 'admin@gmail.com', 'Admin', '$2a$10$2jEcuJBvvY8iLphLQ6male9mDxp0XN9nFK/9jUacyUBDOMYn8gGka', 'Super', 'admin', 1);

insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score, uri) values (41, false, '3bc66c60-50bd-45e1-9b1d-645700f1494a.jpg', 'Con la intención de que Peter tenga una Navidad inolvidable, Mantis y Drax viajan a la Tierra para conseguir el regalo perfecto.', 'Guardianes de la Galaxia: Especial felices fiestas', 2022, 1, 0, '3bc66c60-50bd-45e1-9b1d-645700f1494a');
insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score, uri) values (134, false, 'f1242bd6-beb0-4941-a4ff-28f4bffaa319.jpeg', 'Cumpliendo con su amenaza, el mago Gellert Grindelwald escapa de su custodia y empieza a reunir seguidores con el objetivo de alzar a los magos purasangre para reinar sobre todas las criaturas mágicas.', 'Animales fantásticos: Los crímenes de Grindelwald', 2018, 1, 0,'f1242bd6-beb0-4941-a4ff-28f4bffaa319');
insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score, uri) values (108, false, '8cacb223-5818-4e05-a4ff-a21d977c3c6b.jpg', 'María y Susana, dos adolescentes rebeldes, pasan el verano en un campamento católico. Con la música como denominador común, la rebelión de los adolescentes y el orden eclesiástico chocarán, creando un himno a la libertad y al primer amor.', 'La llamada', 2017, 1, 0, '3bc66c60-50bd-45e1-9b1d-645700f1494a');
insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score, uri) values (94, false, '77c96eea-b009-42c9-b942-1cfab8a7d977.jpg', 'La Familia Addams intenta rescatar a su amado tío Fétido de su nuevo amor, una viuda negra llamada Debbie.', 'La familia Addams: La tradición continúa', 1993, 1, 0, '77c96eea-b009-42c9-b942-1cfab8a7d977');
insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score, uri) values (115, false, 'ca9de12f-80f8-4425-b8e1-ca9283777da3.png', 'Después de presenciar un incidente extraño que involucra a un paciente, la Dra. Rose Cotter comienza a experimentar sucesos aterradores. Rose debe enfrentarse a su inquietante pasado para poder sobrevivir y escapar de su nueva realidad.', 'Smile', 2022, 1, 0, 'ca9de12f-80f8-4425-b8e1-ca9283777da3');
insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score, uri) values (117, false, '3c6cbd62-5c02-4845-870e-ba8e282cdc48.jpg', 'Una versión más oscura del clásico cuento infantil de una marioneta de madera que se transforma en un niño vivo de verdad.', 'Pinocho de Guillermo del Toro', 2022, 1, 0, '3c6cbd62-5c02-4845-870e-ba8e282cdc48');
insert into films (duration, migrate, poster, synopsis, title, year, user_id, avg_score, uri) values (102, false, '038d8597-3112-45e4-96b2-4bc9f072ea2e.jpeg', 'En Colombia, una joven lidia con la frustración de ser la única miembro de la familia que no tiene poderes mágicos.', 'Encanto', 2021, 1, 0, '038d8597-3112-45e4-96b2-4bc9f072ea2e');

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

insert into people (name, surname, type) values ('Barry', 'Sonnenfeld', 'DIRECTOR'); -- 25
insert into people (name, surname, type) values ('Paul', 'Rudnick', 'SCREENWRITER'); -- 26
insert into people (name, surname, type) values ('Anjelica', 'Huston', 'ACTOR'); -- 27
insert into people (name, surname, type) values ('Raul', 'Julia', 'ACTOR'); -- 28
insert into people (name, surname, type) values ('Christopher', 'Lloyd', 'ACTOR'); -- 29
insert into people (name, surname, type) values ('Marc', 'Shaiman', 'MUSICIAN'); -- 30
insert into people (name, surname, type) values ('Donald', 'Peterman', 'PHOTOGRAPHER'); -- 31

insert into people (name, surname, type) values ('Parker', 'Finn', 'DIRECTOR'); -- 32
insert into people (name, surname, type) values ('Parker', 'Finn', 'SCREENWRITER'); -- 33
insert into people (name, surname, type) values ('Sosie', 'Bacon', 'ACTOR'); -- 34
insert into people (name, surname, type) values ('Jessie T.', 'Usher', 'ACTOR'); -- 35
insert into people (name, surname, type) values ('Kyle', 'Gallner', 'ACTOR'); -- 36
insert into people (name, surname, type) values ('Cristobal', 'Tapia de Veer', 'MUSICIAN'); -- 37
insert into people (name, surname, type) values ('Charlie', 'Sarroff', 'PHOTOGRAPHER'); -- 38

insert into people (name, surname, type) values ('Guillermo', 'del Toro', 'DIRECTOR'); -- 39
insert into people (name, surname, type) values ('Guillermo', 'del Toro', 'SCREENWRITER'); -- 40
insert into people (name, surname, type) values ('Ewan', 'McGregor', 'ACTOR'); -- 41
insert into people (name, surname, type) values ('David', 'Bradley', 'ACTOR'); -- 42
insert into people (name, surname, type) values ('Gregory', 'Mann', 'ACTOR'); -- 43
insert into people (name, surname, type) values ('Alexandre', 'Desplat', 'MUSICIAN'); -- 44
insert into people (name, surname, type) values ('Frank', 'Passingham', 'PHOTOGRAPHER'); -- 45

insert into people (name, surname, type) values ('Jared', 'Bush', 'DIRECTOR'); -- 46
insert into people (name, surname, type) values ('Charise Castro', 'Smith', 'SCREENWRITER'); -- 47
insert into people (name, surname, type) values ('Stephanie', 'Beatriz', 'ACTOR'); -- 48
insert into people (name, surname, type) values ('María Cecilia', 'Botero', 'ACTOR'); -- 49
insert into people (name, surname, type) values ('John', 'Leguizamo', 'ACTOR'); -- 50
insert into people (name, surname, type) values ('Germaine', 'Franco', 'MUSICIAN'); -- 51
insert into people (name, surname, type) values ('Alessandro', 'Jacomini', 'PHOTOGRAPHER'); -- 52

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
insert into rel_person_film (film, person) values (4, 25);
insert into rel_person_film (film, person) values (4, 26);
insert into rel_person_film (film, person) values (4, 27);
insert into rel_person_film (film, person) values (4, 28);
insert into rel_person_film (film, person) values (4, 29);
insert into rel_person_film (film, person) values (4, 30);
insert into rel_person_film (film, person) values (4, 31);
insert into rel_person_film (film, person) values (5, 32);
insert into rel_person_film (film, person) values (5, 33);
insert into rel_person_film (film, person) values (5, 34);
insert into rel_person_film (film, person) values (5, 35);
insert into rel_person_film (film, person) values (5, 36);
insert into rel_person_film (film, person) values (5, 37);
insert into rel_person_film (film, person) values (5, 38);
insert into rel_person_film (film, person) values (6, 39);
insert into rel_person_film (film, person) values (6, 40);
insert into rel_person_film (film, person) values (6, 41);
insert into rel_person_film (film, person) values (6, 42);
insert into rel_person_film (film, person) values (6, 43);
insert into rel_person_film (film, person) values (6, 44);
insert into rel_person_film (film, person) values (6, 45);
insert into rel_person_film (film, person) values (7, 46);
insert into rel_person_film (film, person) values (7, 47);
insert into rel_person_film (film, person) values (7, 48);
insert into rel_person_film (film, person) values (7, 49);
insert into rel_person_film (film, person) values (7, 50);
insert into rel_person_film (film, person) values (7, 51);
insert into rel_person_film (film, person) values (7, 52);

