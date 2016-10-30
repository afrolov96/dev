--DB--
create table cities(id integer, name varchar(30));
create table persons(id integer, name varchar(30), patronymic varchar(30), surname varchar(30), city_id integer);
create table cars(id integer, name varchar(30), num varchar(12), color varchar(20), size varchar(3), owner_id integer);


--DATA--
insert into cars(id,name,num,color,size,owner_id)values(1,'AUDI','x123xx196RU','КРАСНЫЙ','C',1);
insert into cars(id,name,num,color,size,owner_id)values(2,'AUDI','a234et174RU','БЕЛЫЙ','D',1);
insert into cars(id,name,num,color,size,owner_id)values(3,'VW','c426ax750RU','ЧЕРНЫЙ','B',2);
insert into cars(id,name,num,color,size,owner_id)values(4,'MB','a404aa123RU','СЕРЫЙ','S',3);
insert into cars(id,name,num,color,size,owner_id)values(5,'VAZ','a109mm66RU','ЖЕЛТЫЙ','B',10);
insert into cars(id,name,num,color,size,owner_id)values(10,'RENAULT','h239kp96RU','ЗЕЛЕНЫЙ','B',10);
insert into cars(id,name,num,color,size,owner_id)values(9,'PEUGEOT','o120ee96RU','ЧЕРНЫЙ','B',5);
insert into cars(id,name,num,color,size,owner_id)values(8,'KIA','x450bk96RU','БЕЛЫЙ','C',6);
insert into cars(id,name,num,color,size,owner_id)values(7,'FORD','b098yp196RU','СЕРЫЙ','C',7);
insert into cars(id,name,num,color,size,owner_id)values(6,'SKODA','a478bx196RU','БЕЛЫЙ','C',8);
 
insert into cities(id,name)values(1,'Екатеринбург');
insert into cities(id,name)values(2,'Москва');
insert into cities(id,name)values(3,'Санкт-Петербург');
insert into cities(id,name)values(4,'Пермь');
insert into cities(id,name)values(5,'Тюмень');
insert into cities(id,name)values(6,'Челябинск');
insert into cities(id,name)values(7,'Омск');
insert into cities(id,name)values(8,'Казань');
insert into cities(id,name)values(9,'Владивосток');
insert into cities(id,name)values(10,'Норильск');
 
insert into persons(id,name,patronymic,surname,city_id)values(1,'Иван','Иванович','Чернов',10);
insert into persons(id,name,patronymic,surname,city_id)values(2,'Ольга','Сергеевна','Жукова',1);
insert into persons(id,name,patronymic,surname,city_id)values(3,'Антон','Петрович','Бородач',1);
insert into persons(id,name,patronymic,surname,city_id)values(4,'Сергей','Петрович','Чехов',6);
insert into persons(id,name,patronymic,surname,city_id)values(5,'Ольга','Андреевна','Иванова',4);
insert into persons(id,name,patronymic,surname,city_id)values(6,'Евгений','Александрович','Кобылин',9);
insert into persons(id,name,patronymic,surname,city_id)values(7,'Анна','Владимировна','Фролова',1);
insert into persons(id,name,patronymic,surname,city_id)values(8,'Денис','Александровна','Потапов',7);
insert into persons(id,name,patronymic,surname,city_id)values(9,'Андрей','Васильевич','Багаев',3);
insert into persons(id,name,patronymic,surname,city_id)values(10,'Юлия','Степановна','Чернова',8);


-- SECURITY --
CREATE TABLE users (user_name varchar(30),password varchar(30));

INSERT INTO users (user_name, password) VALUES ('john', 'johnpass');
INSERT INTO users (user_name, password) VALUES ('tommy', 'tommypass');
INSERT INTO users (user_name, password) VALUES ('terry', 'terrypass');
