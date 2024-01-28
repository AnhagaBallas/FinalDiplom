create table users
(
    id       int auto_increment,
    login    varchar(30) not null unique,
    password varchar(80) not null,
    primary key (id)
);
