create table client
(
    id   bigserial not null primary key,
    name varchar(50) not null
);

create table phone
(
    client_id bigint not null references client (id),
    phone varchar(50)
);

create table address
(
    client_id bigint not null references client (id),
    street varchar(50)
);