Insert Statements
######################
Client Table 
------------
INSERT INTO `mydb`.`client`
(`Cid`,
`First_name`,
`Last_name`,
`Street_addr`,
`City`,
`State`,
`Zip`,
`Email`,
`Cell_number`,
`Ph_number`,
`Level`,
`Oil_owned`)
VALUES
(<{Cid: }>,
<{First_name: }>,
<{Last_name: }>,
<{Street_addr: }>,
<{City: }>,
<{State: }>,
<{Zip: }>,
<{Email: }>,
<{Cell_number: }>,
<{Ph_number: }>,
<{Level: }>,
<{Oil_owned: }>);

Client_auth
------------

INSERT INTO `mydb`.`client_auth`
(`Cid`,
`Password`)
VALUES
(<{Cid: }>,
<{Password: }>);


Commission
-------------
INSERT INTO `mydb`.`commision`
(`Transid`,
`Commision_amount`,
`Curr_dt_oil_price`)
VALUES
(<{Transid: }>,
<{Commision_amount: }>,
<{Curr_dt_oil_price: }>);


Trader
--------
INSERT INTO `mydb`.`trader`
(`Trader_id`,
`Trader_name`)
VALUES
(<{Trader_id: }>,
<{Trader_name: }>);


Transaction
------------

INSERT INTO `mydb`.`transaction`
(`Transid`,
`Cid`,
`Oil_amt`,
`Oil_owed`,
`Trans_date`,
`Cash_owed`,
`Trader_id`,
`Oil_paid`,
`Cash_paid`,
`Status`)
VALUES
(<{Transid: }>,
<{Cid: 0}>,
<{Oil_amt: }>,
<{Oil_owed: }>,
<{Trans_date: }>,
<{Cash_owed: }>,
<{Trader_id: }>,
<{Oil_paid: }>,
<{Cash_paid: }>,
<{Status: }>);


######################
Update Statements
######################

Level Update of Client(Point 3)
-----------------------

Update client set Level = 'Gold' where 
Cid in (
select cid from transaction group by cid, month(Trans_date) having sum(oil_amt)>30) and  level='Silver' ; 
//Add one for specific cid or even better for a Transid

Selling oil(Point 4) 
----------------
#Select Query for retreving the amout of oil

Select oil_owned from client;
# Updating the oil amount

Update client set oil_owned =oil_owned-$oil_sold where cid='&cid';




Settling Account(POINT 7)
-----------------

Update transaction set Oil_owed=oil_owed-paid_oil, cash_owed=cash_owed-paid_cash,Oil_paid=paid_oil, cash_owed=paid_cash where cid= ? and Transid=

Select Statement for Oil transation(Point 4)
------------------------------------
Select Oil_owned from client where cid='' and Oil_owned>req_amt;

Notes:
Point 8: We need to keep track of trasnactions that are cancelled.

 






