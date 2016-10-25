--DB--
create table cities(id number, name varchar(30));
create table persons(id number,name varchar(30),patronymic varchar(30),surname varchar(30),city_id number);
create table cars(id number,model varchar(30),num varchar(12),color varchar(20),type varchar(2), owner_id number);


--DATA--
insert into cars(id,model,num,color,type,owner_id)values(1,'AUDI','x123xx196RU','RED','C',1);
insert into cars(id,model,num,color,type,owner_id)values(2,'AUDI','a234et174RU','WHITE','D',1);
insert into cars(id,model,num,color,type,owner_id)values(3,'VW','c426ax750RU','BLACK','B',2);
insert into cars(id,model,num,color,type,owner_id)values(4,'MB','a404aa123RU','GREY','S',3);
insert into cars(id,model,num,color,type,owner_id)values(5,'VAZ','a109mm66RU','YELLOW','B',10);
insert into cars(id,model,num,color,type,owner_id)values(10,'RENAULT','h239kp96RU','GREEN','B',10);
insert into cars(id,model,num,color,type,owner_id)values(9,'PEUGEOT','o120ee96RU','BLACK','B',5);
insert into cars(id,model,num,color,type,owner_id)values(8,'KIA','x450bk96RU','WHITE','C',6);
insert into cars(id,model,num,color,type,owner_id)values(7,'FORD','b098yp196RU','GREY','C',7);
insert into cars(id,model,num,color,type,owner_id)values(6,'SKODA','a478bx196RU','WHITE','C',8);
 
insert into cities(id,name)values(1,'Yekaterinburg');
insert into cities(id,name)values(2,'Moscow');
insert into cities(id,name)values(3,'St.Peterburg');
insert into cities(id,name)values(4,'Perm');
insert into cities(id,name)values(5,'Tyumen');
insert into cities(id,name)values(6,'Chelyabinsk');
insert into cities(id,name)values(7,'Omsk');
insert into cities(id,name)values(8,'Kazan');
insert into cities(id,name)values(9,'Vladivostok');
insert into cities(id,name)values(10,'Norilsk');
 
insert into persons(id,name,patronymic,surname,city_id)values(1,'ivan','ivanovich','chehov',10);
insert into persons(id,name,patronymic,surname,city_id)values(2,'olga','sergeevna','zhukova',1);
insert into persons(id,name,patronymic,surname,city_id)values(3,'anton','petrovich','borodach',1);
insert into persons(id,name,patronymic,surname,city_id)values(4,'sergey','petrovich','chehov',6);
insert into persons(id,name,patronymic,surname,city_id)values(5,'olga','andreevna','ivanova',4);
insert into persons(id,name,patronymic,surname,city_id)values(6,'evgeniy','aleksandrovich','kobylin',9);
insert into persons(id,name,patronymic,surname,city_id)values(7,'anna','vladimirovna','frolova',1);
insert into persons(id,name,patronymic,surname,city_id)values(8,'denis','aleksandrovich','potapov',7);
insert into persons(id,name,patronymic,surname,city_id)values(9,'andrey','vasilyevich','bagaev',3);
insert into persons(id,name,patronymic,surname,city_id)values(10,'yuliya','stepanovna','chernova',8);


-- SECURITY --
CREATE TABLE users (user_name varchar(30),password varchar(30));

INSERT INTO users (user_name, password) VALUES ('john', 'johnpass');
INSERT INTO users (user_name, password) VALUES ('tommy', 'tommypass');
INSERT INTO users (user_name, password) VALUES ('terry', 'terrypass');
