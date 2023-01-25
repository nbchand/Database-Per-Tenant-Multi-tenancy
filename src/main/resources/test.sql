CREATE TABLE if not exists public.data_source_config (
    id bigint PRIMARY KEY,
    driver_class_name VARCHAR(255) not null,
    url VARCHAR(255) not null,
    name VARCHAR(255) not null unique,
    username VARCHAR(255) not null,
    password VARCHAR(255) not null
    );


INSERT INTO data_source_config VALUES (1, 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/test1', 'test1', 'postgres', 'postgres');
INSERT INTO data_source_config VALUES (2, 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/test2', 'test2', 'postgres', 'postgres');