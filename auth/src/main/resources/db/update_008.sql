create table IF NOT EXISTS employee (
    id serial primary key not null,
    firstname varchar(2000),
    lastname varchar(2000),
    ssn varchar(2000),
    hiredate timestamp
);

create table IF NOT EXISTS person (
    id serial primary key not null,
    login varchar(2000),
    password varchar(2000)
);

create table IF NOT EXISTS employee_accounts (
    id serial primary key not null,
    employee_id int not null references employee(id),
    accounts_id int not null references person(id)
);

insert into employee (firstname, lastname, ssn, hiredate) values ('Thomas', 'Edison', '112-121-11', now());
insert into person (login, password) values ('root', '123');
