-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "pojo" database.
-------------------------------------------------------------------------------
INSERT INTO Category VALUES (1,'Futbol');
INSERT INTO Category VALUES (2,'Baloncesto');
INSERT INTO Category VALUES (3,'Dardos');
INSERT INTO Category VALUES (4,'Motor');

-- ----------------------------------------------------------------------------
INSERT INTO Event VALUES (1,'Deportivo - Real Madrid', '2016-06-15 20:00:00',1);
INSERT INTO Event VALUES (2,'Real Madrid - Manchester City', '2016-05-04 20:45:00',1);
INSERT INTO Event VALUES (3,'Coruxo - Rapido de Bouzas', '2016-05-28 17:00:00',1);
INSERT INTO Event VALUES (4,'España - Alemania', '2016-05-20 19:30:00',1);
INSERT INTO Event VALUES (5,'Celta - Deportivo', '2016-07-15 12:00:00',1);
INSERT INTO Event VALUES (6,'Levante - Las Palmas', '2016-08-12 20:45:00',1);
INSERT INTO Event VALUES (7,'Van gerwen - Phil Taylor', '2016-06-23 18:00:00',3);
INSERT INTO Event VALUES (8,'Toronto Raptors - Miami Heat', '2016-05-20 00:30:00',2);
INSERT INTO Event VALUES (9,'MotoGp - Le Mans', '2016-06-05 16:45:00',4);
INSERT INTO Event VALUES (10,'Formula1 - Abu Dhabi', '2016-05-09 20:45:00',4);
INSERT INTO Event VALUES (11,'Golden State Warriors - Atlanta Hawks', '2016-05-28 17:00:00',2);
INSERT INTO Event VALUES (12,'Roquetas de Lepe - Rapido de Pontequieto','2016-06-25 12:00:00',1);
INSERT INTO Event VALUES (13,'Rapido de Pontequieto - Roquetas de Lepe','2016-05-4 12:00:00',1);

-- ----------------------------------------------------------------------------
INSERT INTO BetType VALUES (1,'Resultado del partido?',1,true);
INSERT INTO BetType VALUES (2,'Quien marcara?',2,false);
INSERT INTO BetType VALUES (3,'Resultado del partido?',2,true);
INSERT INTO BetType VALUES (4,'Resultado del partido?',13,true);


-- ----------------------------------------------------------------------------
INSERT INTO OptionB VALUES (1,'1',2.00,null,1,1);
INSERT INTO OptionB VALUES (2,'X',1.50,null,1,1);
INSERT INTO OptionB VALUES (3,'2',1.10,null,1,1);
INSERT INTO OptionB VALUES (4,'Cristiano',1.08,1,2,1);
INSERT INTO OptionB VALUES (5,'Arbeloa',10.50,0,2,1);
INSERT INTO OptionB VALUES (6,'Bale',1.10,0,2,1);
INSERT INTO OptionB VALUES (7,'1',1.30,null,3,1);
INSERT INTO OptionB VALUES (8,'X',1.50,null,3,1);
INSERT INTO OptionB VALUES (9,'2',2.10,null,3,1);
INSERT INTO OptionB VALUES (10,'1',1.30,null,4,1);
INSERT INTO OptionB VALUES (11,'X',1.50,null,4,1);
INSERT INTO OptionB VALUES (12,'2',2.10,null,4,1);

-- ----------------------------------------------------------------------------
INSERT INTO UserProfile VALUES (1,'admin','JZEt/QSNqUvt6','admin','admin','admin@admin');
INSERT INTO UserProfile VALUES (2,'user','NAOnzlpcw5EMQ','user','user','user@user');
INSERT INTO UserProfile VALUES (3,'pepe','ZOpxb77A5a6KI','pepe','pepe','pepe@pepe');

-- ----------------------------------------------------------------------------
INSERT INTO Bet VALUES (1,1,'2016-05-05 16:00:00',1,2);
INSERT INTO Bet VALUES (2,2,'2016-05-05 16:01:00',4,2);
INSERT INTO Bet VALUES (3,3,'2016-05-05 16:02:30',5,2);
INSERT INTO Bet VALUES (4,4,'2016-05-05 16:03:00',2,2);
INSERT INTO Bet VALUES (5,5,'2016-05-05 16:04:00',3,2);
INSERT INTO Bet VALUES (6,6,'2016-05-05 16:05:30',6,2);
INSERT INTO Bet VALUES (7,7,'2016-05-05 16:06:00',7,2);
INSERT INTO Bet VALUES (8,8,'2016-05-05 16:07:00',8,2);
INSERT INTO Bet VALUES (9,9,'2016-05-05 16:08:00',9,2);
INSERT INTO Bet VALUES (10,10,'2016-05-05 16:09:00',10,2);
INSERT INTO Bet VALUES (11,11,'2016-05-05 16:10:00',11,2);
INSERT INTO Bet VALUES (12,12,'2016-05-05 16:11:30',12,2);
INSERT INTO Bet VALUES (13,13,'2016-05-05 16:12:00',1,2);
INSERT INTO Bet VALUES (14,14,'2016-05-05 16:13:00',4,2);
INSERT INTO Bet VALUES (15,15,'2016-05-05 16:14:30',5,2);
INSERT INTO Bet VALUES (16,16,'2016-05-05 16:15:00',2,2);
INSERT INTO Bet VALUES (17,17,'2016-05-05 16:16:00',3,2);
INSERT INTO Bet VALUES (18,18,'2016-05-05 16:17:30',6,2);
INSERT INTO Bet VALUES (19,19,'2016-05-05 16:18:00',7,2);
INSERT INTO Bet VALUES (20,20,'2016-05-05 16:19:00',8,2);
INSERT INTO Bet VALUES (21,21,'2016-05-05 16:20:30',9,2);
INSERT INTO Bet VALUES (22,22,'2016-05-05 16:21:00',10,2);
INSERT INTO Bet VALUES (23,23,'2016-05-05 16:22:00',11,2);
INSERT INTO Bet VALUES (24,24,'2016-05-05 16:23:30',12,2);







