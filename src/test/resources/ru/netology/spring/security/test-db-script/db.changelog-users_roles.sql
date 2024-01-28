CREATE TABLE users_roles
(
    user_id int auto_increment not null,
    role_id int                not null,
    primary key (user_id, role_id),
    foreign key (user_id) references netology.users (id),
    foreign key (role_id) references netology.roles (id)
);
