CREATE TABLE IF NOT EXISTS roles
(
    id   smallserial primary key not null unique,
    name varchar(20) unique
);

INSERT INTO roles(name)
VALUES ('ROLE_USER'),
       ('ROLE_MODERATOR'),
       ('ROLE_ADMIN');

CREATE TABLE IF NOT EXISTS users
(
    id       bigserial primary key not null unique,
    username varchar(50) unique    not null,
    email    varchar(100) unique   not null,
    password text
);

CREATE TABLE IF NOT EXISTS user_roles
(
    role_id smallint not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles,
    user_id bigint  not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users,
    constraint user_roles_pkey
        primary key (user_id, role_id)
);
