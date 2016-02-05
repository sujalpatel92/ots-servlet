SET SQL_SAFE_UPDATES = 0;
delete from  auth;
delete from  commision;
delete from  transaction;
Delete from client;
delete from trader;
commit;

#6 clients, 4 traders, 1 manager
insert into auth values(1,md5('har_23'),0);
insert into auth values(2,md5('trial1'),0);
insert into auth values(3,md5('targ_5'),0);
insert into auth values(4,md5('tom_hawk_44'),0);
insert into auth values(5,md5('f-56'),0);
insert into auth values(6,md5('f_14'),0);
insert into auth values(1,md5('abc123'),1);
insert into auth values(4,md5('trial_43'),1);
insert into auth values(3,md5('target_4'),1);
insert into auth values(2,md5('cde456'),1);
insert into auth values(1,md5('anr_55'),2);

select * from auth;

#client

ALTER TABLE client AUTO_INCREMENT = 1;

INSERT INTO client
(`First_name`,`Last_name`,`Street_addr`,`City`,`State`,`Zip`,`Email`,`Cell_number`,`Ph_number`,`level`,`oil_owned`)
VALUES('Gloria','Foster','86 Grim Plaza','Pittsburgh','Pennsylvania','15279','gfoster1@tinypic.com','4125329016','2055949933','Silver',25);

INSERT INTO client
(`First_name`,`Last_name`,`Street_addr`,`City`,`State`,`Zip`,`Email`,`Cell_number`,`Ph_number`,`level`,`oil_owned`)
VALUES('Alan','Kelley','9 Lotheville Plaza','El Paso','Texas','79928','akelley2@blogspot.com','9154534886','9379990600','Silver',20);

INSERT INTO client
(`First_name`,`Last_name`,`Street_addr`,`City`,`State`,`Zip`,`Email`,`Cell_number`,`Ph_number`,`level`,`oil_owned`)
VALUES ('Teresa','Carroll','9983 Springview Hill','Topeka','Kansas','66611','tcarroll3@salon.com','785653781','8591756629','Silver',10);

INSERT INTO client
(`First_name`,`Last_name`,`Street_addr`,`City`,`State`,`Zip`,`Email`,`Cell_number`,`Ph_number`,`level`,`oil_owned`)
VALUES('Melissa','Cook','4 Becker Junction','Columbus','Georgia','31998','mcook4@va.gov','706194960','6193082340','Silver',15);

INSERT INTO client
(`First_name`,`Last_name`,`Street_addr`,`City`,`State`,`Zip`,`Email`,`Cell_number`,`Ph_number`,`level`,`oil_owned`)
VALUES('Peter','Coleman','200 Dexter Alley','Oakland','California','94605','pcoleman5@hhs.gov','5105681662','8581160779','Gold',80);

INSERT INTO client
(`First_name`,`Last_name`,`Street_addr`,`City`,`State`,`Zip`,`Email`,`Cell_number`,`Ph_number`,`level`,`oil_owned`)
VALUES('Deborah','Arnold','94 Almo Park','Richmond','Virginia','23237','darnold6@hexun.com','8043389440','5046307657','Gold',150);
commit;
select * from client;

#Trader
ALTER TABLE trader AUTO_INCREMENT = 1;

INSERT INTO trader (`Trader_name`)
VALUES ('Martin Evans');

INSERT INTO trader (`Trader_name`)
VALUES ('Mark Miller');

INSERT INTO trader (`Trader_name`)
VALUES('Gary Stewart');

INSERT INTO trader (`Trader_name`)
VALUES('Martha Johnson');

commit;
 
select * from trader;

# 4 transsactions
ALTER TABLE transaction AUTO_INCREMENT = 1;

INSERT INTO `transaction`(`Cid`,`Oil_amt`,`Oil_owed`,`Trans_date`,`Cash_owed`,`Trader_id`,`Oil_paid`,
`Cash_paid`,`Status`,`Trans_type`,`Dues_settled`)VALUES(1,25,0,curdate(),600,1,5,500,'Pending',0,'Y');	

INSERT INTO `transaction`
(`Cid`,`Oil_amt`,`Oil_owed`,`Trans_date`,`Cash_owed`,`Trader_id`,`Oil_paid`,`Cash_paid`,`Status`,`Trans_type`,`Dues_settled`)
VALUES(2,20,2,curdate(),360,4,5,50,'Approved',0,'Y');	

INSERT INTO `transaction`
(`Cid`,`Oil_amt`,`Oil_owed`,`Trans_date`,`Cash_owed`,`Trader_id`,`Oil_paid`,`Cash_paid`,`Status`,`Trans_type`,`Dues_settled`)
VALUES(3,5,0,curdate(),5000,3,90.31,6887.98,'Pending',0,'Y');	

INSERT INTO `transaction`
(`Cid`,`Oil_amt`,`Oil_owed`,`Trans_date`,`Cash_owed`,`Trader_id`,`Oil_paid`,`Cash_paid`,`Status`,`Trans_type`,`Dues_settled`)
VALUES(4,15,1.93,curdate(),5641.45,2,22.8,4197.89,'Approved',0,'Y');	

select * from transaction
commit;

#Commision

insert into commision values (1,5,50,1);
insert into commision values (2,200,50,0);
insert into commision values (3,1,50,1);
insert into commision values (4,150,50,0);
select * from commision;
commit;