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
  * table A和B是一对多的关系，求A对应在B的某column的avg。
  * http://www.w3schools.com/sql/trysql.asp?filename=trysql_select_join
  * =: have no space on both side
  * join need on
  * group by is at last
  * CustomerID need specify its table name
  */
 select Customers.CustomerID, OrderID, avg(EmployeeID)
 from  Customers left join orders
 on Customers.customerid=orders.customerid
 where Customers.customerid in ('5', '4')
 group by Customers.CustomerID

