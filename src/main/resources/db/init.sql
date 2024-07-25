DROP TABLE IF EXISTS users, users_roles, roles;

create table users (
                       id                    int not null auto_increment,
                       name              	varchar(100) not null unique,
                       lastname				varchar(100) not null,
                       password              varchar(100) not null,
                       email                 varchar(100) unique,
                       age					int not null,

                       primary key (id)
);

create table roles (
                       role_id                   int not null auto_increment,
                       type                  	varchar(100) not null,
                       primary key (role_id)
);

CREATE TABLE users_roles (
                             id					int not null auto_increment,
                             user_id               int not null,
                             role_id               int not null,
                             primary key (id),
                             foreign key (user_id) references users (id),
                             foreign key (role_id) references roles (role_id)
);

insert into roles (type)
values
    ('ROLE_USER'),
    ('ROLE_ADMIN');

# password == "password" (bcrypt)
insert into users (name, lastname, password, email, age)
values
    ('user', 'lastname', '$2a$12$FNsE4CRSQMm1IFRdw1r8R.iHP.MBTpkPAErKr1eh/Zt5Ndve8ns7a', 'user@gmail.com', 50),
    ('admin', 'lastname', '$2a$12$FNsE4CRSQMm1IFRdw1r8R.iHP.MBTpkPAErKr1eh/Zt5Ndve8ns7a', 'admin@gmail.com', 10);

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);