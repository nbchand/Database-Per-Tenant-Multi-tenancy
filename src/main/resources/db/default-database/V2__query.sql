create table data_source_config
(
    id                bigint       not null
        primary key,
    driver_class_name varchar(255) not null,
    url               varchar(255) not null,
    name              varchar(255) not null
        unique,
    username          varchar(255) not null,
    password          varchar(255) not null
);

alter table data_source_config
    owner to postgres;

INSERT INTO public.data_source_config (id, driver_class_name, url, name, username, password) VALUES (1, 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/test', 'public', 'postgres', 'postgres');
INSERT INTO public.data_source_config (id, driver_class_name, url, name, username, password) VALUES (2, 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/test1', 'test1', 'postgres', 'postgres');
INSERT INTO public.data_source_config (id, driver_class_name, url, name, username, password) VALUES (3, 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/test2', 'test2', 'postgres', 'postgres');