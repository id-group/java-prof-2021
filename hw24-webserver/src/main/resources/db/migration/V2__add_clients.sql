create sequence address_sequence start with 1 increment by 1;

insert into address(id, street) VALUES (nextval('address_sequence'), 'Тверская-Ямская ул.');
insert into client(id, name, address_id) VALUES (nextval('hibernate_sequence'), 'Мистер Пупкин', currval('address_sequence'));
