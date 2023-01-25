CREATE TABLE if not exists public.employee
(
    id          bigint PRIMARY KEY,
    name        VARCHAR(255) not null,
    designation VARCHAR(255) not null
    );

create sequence hibernate_sequence;
alter sequence hibernate_sequence owner to postgres;