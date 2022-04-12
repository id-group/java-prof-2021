-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence hibernate_sequence start with 1 increment by 1;

create table users
(
    id   bigint not null primary key,
    name varchar(50) unique,
    login varchar(150),
    password varchar(100)
);

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id bigint
);

create table address
(
    id   bigint not null primary key,
    street varchar(250)
);

create table phone
(
    id   bigint not null primary key,
    street varchar(250),
    client_id bigint,
    number varchar(50)
);

insert into users( id, name, login, password) VALUES ( nextval('hibernate_sequence'), 'user1', 'user1', '111111');
insert into users( id, name, login, password) VALUES ( nextval('hibernate_sequence'), 'user2', 'user2', '111111');
