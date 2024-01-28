
create table users
(
    id       int auto_increment,
    login    varchar(30) not null unique,
    password varchar(80) not null,
    primary key (id)
);

create table roles
(
    id   int auto_increment,
    name varchar(50) not null,
    primary key (id)
);

CREATE TABLE users_roles
(
    user_id int auto_increment not null,
    role_id int                not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

CREATE TABLE files
(
    files_id     int auto_increment not null,
    filename     varchar(80),
    login        varchar(30)        not null,
    file         blob,
    content_type varchar(80),
    size         bigint,
    primary key (files_id),
    foreign key (login) references users (login)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (login, password)
values ('user@gmail.com', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i'),
       ('admin@gmail.com', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

