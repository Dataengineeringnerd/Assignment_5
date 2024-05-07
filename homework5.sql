CREATE DATABASE mydb;
create table mydb.login(username varchar(20), password varchar(20));
insert into mydb.login(username, password) values ('a', 'b'), ('mmc', 'mymmc');


create table mydb.patient(ID varchar(10), NAME varchar(40), SSN varchar(20));
insert into mydb.patient(ID, NAME, SSN) values (1001, 'John Smith', '111-111-111'), (1002, 'Dennis Doe', '222-222-222'), (1003, 'Grace Ritchie', '333-333-333');

SELECT * FROM mydb.patient
