create table films (id bigint not null auto_increment, date_migrate datetime, duration integer not null, migrate bit not null, poster varchar(255) not null, synopsis varchar(255), title varchar(255) not null, year integer not null, user_id bigint, primary key (id));

insert into roles (name) values ('ADMIN');
insert into roles (name) values ('USER');

insert into users (active, birthdate, creation_date, email, name, password, surname, username, role_id) values (true, '1990-01-01 18:33:09.242000', now(), 'admin@gmail.com', 'Admin', '$2a$10$2jEcuJBvvY8iLphLQ6male9mDxp0XN9nFK/9jUacyUBDOMYn8gGka', 'Super', 'admin', 1);