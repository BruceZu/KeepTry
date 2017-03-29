
--   www.hackerrank.com test used by TrialPay a Visa company
--   Find the 5 oldest (earliest) orders which are not yet shipped.
--   (rownum is Oracle SQL) asc is default

 select orderNumber, orderDate, requiredDate, shippedDate, status,  comments, customerNumber
 from orders
 where  status <> 'Shipped'  and rownum <= 5
 order by orderDate;
