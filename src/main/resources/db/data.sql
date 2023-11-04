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

/*INSERT INTO Documents (id, type, GDrive_Link, creation_date)
VALUES  (1,0,'<GLinkDrive1>','2023-10-29'),
        (2,0,'<GLinkDrive2>','2023-10-28'),
        (3,0,'<GLinkDrive3>','2023-10-27');*/

INSERT INTO Partnership_Contracts(id, type, GDrive_Link, creation_date,start_date,end_date,rate,donation_percentage)
VALUES  (4,0,'<GLinkDrive4>','2023-10-29','2023-10-30','2023-11-29',1.0,69),
        (5,0,'<GLinkDrive5>','2023-10-28','2023-11-01','2023-11-29',0.9,10),
        (6,0,'<GLinkDrive6>','2023-10-27','2023-11-02','2023-11-29',1.1,99);

INSERT INTO Additional_Packages(id, type, GDrive_Link, creation_date,package_type,price)
VALUES  (7,2,'<GLinkDrive7>','2023-10-29','LEET',133.7),
        (8,3,'<GLinkDrive8>','2023-10-28','HEHE',69.69),
        (9,4,'<GLinkDrive9>','2023-10-27','VIP Premium',2137.0);

INSERT INTO Subscriptions(id, type, GDrive_Link, creation_date,start_date,period,monthly_rate)
VALUES  (10,2,'<GLinkDrive10>','2023-10-29','2023-10-30',1,69.99),
        (11,3,'<GLinkDrive11>','2023-10-28','2023-11-01',2,13.37),
        (12,4,'<GLinkDrive12>','2023-10-27','2023-11-02',3,21.37);

INSERT INTO Monthly_Reports(id, type, GDrive_Link, creation_date,start_date,end_date,viewers,hours_watched,donations)
VALUES  (13,2,'<GLinkDrive13>','2023-01-01','2023-09-01','2023-09-30',1111,45649.5,123123),
        (14,3,'<GLinkDrive14>','2023-10-01','2023-09-01','2023-09-30',20332,123333.3,3455),
        (15,4,'<GLinkDrive15>','2023-10-01','2023-09-01','2023-09-30',2137,69000.5,666);


