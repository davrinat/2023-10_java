-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
drop table if exists client cascade;
drop table if exists address;
drop table if exists phone;

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    client_name varchar(50),
    address_id bigint
);

create sequence address_seq start with 1 increment by 1;

create table address
(
    id   bigint not null primary key,
    street varchar(50)
);

create sequence phone_seq start with 1 increment by 1;

create table phone
(
    id   bigint not null primary key,
    number varchar(50),
    client_id bigint
);