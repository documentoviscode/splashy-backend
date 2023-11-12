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

/*
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

*/
