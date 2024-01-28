CREATE TABLE files
(
    files_id     int auto_increment not null,
    filename     varchar(80),
    login        varchar(30)        not null,
    file         blob,
    content_type varchar(80),
    size         bigint,
    primary key (files_id),
    foreign key (login) references netology.users (login)
);

