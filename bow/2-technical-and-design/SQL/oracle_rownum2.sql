--  SQL server: top
--  MySQL: limited
--  Oracle: rownum

SELECT TOP 1  employee_id , salary FROM [employee]  order by salary desc
SELECT        employee_id , salary FROM [employee]  order by salary desc limit 1
select        employee_id , salary from (select employee_id, salary from [employee] order by salary desc) where rownum=1;
