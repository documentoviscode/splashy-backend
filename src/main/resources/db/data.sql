INSERT INTO Users (id, name, surname, email, role, avatar)
VALUES  (1,'Cezary','Krawczyk','cezary.krawczyk@gmail.com',2,'admin.png');

INSERT INTO Partners (id, name, surname, nickname, email, role, avatar,account_number,phone_number,pesel)
VALUES  (2,'Alicja','Wróblewska', 'queenAlicja', 'alicja.wroblewska@wp.pl',1,'partner1.png',84501634,'510 642 755','98050293235'),
        (3,'Igor','Kazmierczak', 'igorek845', 'Igor.Kazmierczak@gmail.com',1,'partner2.png',54791546,'784 826 166','86072435742'),
        (4,'Maja','Sokołowska', 'majaTV', 'Maja.Sokolowska@gmail.com',1,'partner3.png',654489271,'621 496 178','99071095279');

INSERT INTO Credit_cards (id, number, cvc, expiration_Date)
VALUES  (1,'5504737089315318','583','2027-09-01');

INSERT INTO Clients (id, name, surname, email, role, avatar,credit_card_id)
VALUES  (5,'Ernest','Kołodziej','ernest.kolodziej@gmail.com',0,'client.png',1);

/*INSERT INTO Documents (id, type, GDrive_Link, creation_date)
VALUES  (1,0,'<GLinkDrive1>','2023-10-29'),
        (2,0,'<GLinkDrive2>','2023-10-28'),
        (3,0,'<GLinkDrive3>','2023-10-27');*/

INSERT INTO Partnership_Contracts(id, type, GDrive_Link, creation_date,start_date,end_date,rate,donation_percentage, user_id)
VALUES  (4, 0,'<GLinkDrive4>','2023-08-01','2023-08-01','2023-09-30',1.0,69, 2), --Alicja first contract
        (5, 0,'<GLinkDrive5>','2023-10-01','2023-10-01','2023-11-30',1.2,80, 2), --Alicja second contract
        (6, 0,'<GLinkDrive6>','2023-09-01','2023-09-01','2023-11-30',0.9,10, 3), --Igor first contract
        (7, 0,'<GLinkDrive7>','2023-09-01','2023-09-01','2023-11-30',1.1,99, 4); --Maja first contract

/*
INSERT INTO Additional_Packages(id, type, GDrive_Link, creation_date,package_type,price, user_id)
VALUES  (x,2,'<GLinkDrivex>','2023-10-29','LEET',133.7, 2),
        (x,3,'<GLinkDrivex>','2023-10-28','HEHE',69.69, 3),
        (x,4,'<GLinkDrivex>','2023-10-27','VIP Premium',2137.0, 4);
*/

INSERT INTO Subscriptions(id, type, GDrive_Link, creation_date,start_date,period,monthly_rate, user_id)
VALUES  (10,2,'<GLinkDrive10>','2023-10-01','2023-10-01',1,69.99, 5); --Ernest subscription

INSERT INTO Monthly_Reports(id, type, GDrive_Link, creation_date,start_date,end_date,viewers,hours_watched,donations, user_id)
VALUES  (13,2,'<GLinkDrive13>','2023-08-01','2023-08-01','2023-08-31',1111,454545.5,1232, 2), --Alicja August monthly report
        (14,3,'<GLinkDrive14>','2023-09-01','2023-09-01','2023-09-30',2342,985945,2342, 2), --Alicja September monthly report
        (15,3,'<GLinkDrive15>','2023-10-01','2023-10-01','2023-10-31',10003,123333.3,3455, 2), --Alicja October monthly report
        (16,3,'<GLinkDrive16>','2023-11-01','2023-11-01','2023-11-30',20332,256032,4500, 2), --Alicja November monthly report
        (17,3,'<GLinkDrive17>','2023-09-01','2023-09-01','2023-09-31',20332,502532,9504, 3), --Igor September monthly report
        (18,3,'<GLinkDrive18>','2023-10-01','2023-10-01','2023-10-30',35322,1232522.3,1002, 3), --Igor October monthly report
        (19,3,'<GLinkDrive19>','2023-09-01','2023-09-01','2023-09-31',532,64321.3,321, 4), --Maja September monthly report
        (20,3,'<GLinkDrive20>','2023-10-01','2023-10-01','2023-10-30',513,35123,235, 4); --Maja October monthly report
