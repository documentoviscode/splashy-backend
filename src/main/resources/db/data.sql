INSERT INTO Users (id, name, surname, email, role, avatar)
VALUES  (1,'Cezary','Krawczyk','Cezary.Krawczyk@gmail.com',2,'tempAvatar1'),
        (2,'Andrzej','Zakrzewska','Andrzej.Zakrzewska@gmail.com',2,'tempAvatar2'),
        (3,'Dorota','Bak','Dorota.Bak@gmail.com',2,'tempAvatar3');

INSERT INTO Partners (id, name, surname, email, role, avatar,account_number,phone_number,pesel)
VALUES  (4,'Ernest','Kolodziej','Ernest.Kolodziej@gmail.com',1,'tempAvatar4',123,'111 111 111','11111111111'),
        (5,'Igor','Kazmierczak','Igor.Kazmierczak@gmail.com',1,'tempAvatar5',124,'222 222 222','22222222222'),
        (6,'Maja','Sokolowska','Maja.Sokolowska@gmail.com',1,'tempAvatar6',125,'333 333 333','33333333333'),
        (7,'Alicja','Chmielewska','Alicja.Chmielewska@gmail.com',1,'tempAvatar7',126,'444 444 444','44444444444');

INSERT INTO Credit_cards (id, number, cvc, expiration_Date)
VALUES  (1,'123123','123','2023-12-31'),
        (2,'334343','321','2024-01-01'),
        (3,'454545','111','2024-02-02'),
        (4,'888888','098','2024-03-03');

INSERT INTO Clients (id, name, surname, email, role, avatar,credit_card_id)
VALUES  (8,'Ernest','Kolodziej','Ernest.Kolodziej@gmail.com',0,'tempAvatar8',1),
        (9,'Igor','Kazmierczak','Igor.Kazmierczak@gmail.com',0,'tempAvatar9',2),
        (10,'Maja','Sokolowska','Maja.Sokolowska@gmail.com',0,'tempAvatar10',3),
        (11,'Alicja','Chmielewska','Alicja.Chmielewska@gmail.com',0,'tempAvatar11',4);

