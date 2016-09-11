/**
 * www.hackerrank.com test used by TrialPay a Visa company
 * Find the 5 oldest (earliest) orders which are not yet shipped.
 * (rownum is Oracle SQL)
 */

 select orderNumber, orderDate, requiredDate, shippedDate, status,  comments, customerNumber
 from orders
 where  status <> 'Shipped'  and rownum <= 5
 order by orderDate asc;


 /**
  * table A:B is 1:n，search avg(column in B) group by column in A.
  *
  * http://www.w3schools.com/sql/trysql.asp?filename=trysql_select_join
  * have no space on both side of =
  * join need on
  * CustomerID need specify its table name
  * 'group by' is at last
  */
 select Customers.CustomerID, OrderID, avg(EmployeeID)
 from  Customers left join orders
 on Customers.customerid=orders.customerid
 where Customers.customerid in ('5', '4')
 group by Customers.CustomerID;


