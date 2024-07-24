DROP TABLE IF EXISTS users, users_roles, roles;

create table users (
  id                    int not null auto_increment,
  username              varchar(100) not null unique,
  password              varchar(100) not null,
  email                 varchar(100) unique,
  primary key (id)
);

create table roles (
  id                    int not null auto_increment,
  name                  varchar(100) not null,
  primary key (id)
);

CREATE TABLE users_roles (
  id					int not null auto_increment,
  user_id               int not null,
  role_id               int not null,
  primary key (id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

# password == "password" (bcrypt)
insert into users (username, password, email)
values
('user', '$2a$12$FNsE4CRSQMm1IFRdw1r8R.iHP.MBTpkPAErKr1eh/Zt5Ndve8ns7a', 'user@gmail.com'),
('admin', '$2a$12$FNsE4CRSQMm1IFRdw1r8R.iHP.MBTpkPAErKr1eh/Zt5Ndve8ns7a', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);