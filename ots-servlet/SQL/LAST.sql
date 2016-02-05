#select * from transaction;
#daily Plug in the entered date value in the statement '2015-11-23' and '2015-11-28'_

select Trans_date Date,sum(Cash_paid) cash_received,(oil_amt) oil_sold,sum(oil_owed) oil_outstanding, sum(cash_owed) cash_outstanding
 from transaction where date(trans_date) between '2015-11-23' and '2015-11-28' 
group by Trans_date;
#Weekly
select week(Trans_date) Week,sum(Cash_paid) cash_received,(oil_amt) oil_sold,sum(oil_owed) oil_outstanding, sum(cash_owed) cash_outstanding
 from transaction where date(trans_date) between '2015-11-23' and '2015-11-28' 
group by week(Trans_date);
#monthly review
select month(Trans_date) Month,sum(Cash_paid) cash_received,(oil_amt) oil_sold,sum(oil_owed) oil_outstanding, sum(cash_owed) cash_outstanding
 from transaction where date(trans_date) between '2015-11-23' and '2015-11-28' 
group by month(Trans_date);

