create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

insert into client(name) values ('test');
insert into client(name) values ('test1');

create table manager
(
   no bigserial not null primary key,
   label varchar(250),
   private varchar(250)
);

insert into manager(label, private) values ('label', 'test');

