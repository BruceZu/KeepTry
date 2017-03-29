--2nd maximum
SQL>  select * from test where id = (select id from (
                                                select t1.id, count(1) as c
                                                from test t1, test t2
                                                where t1.salary <=t2.salary
                                                group by t1.id)
                                     where c=2)

SQL>  select  * from test t1 inner join test t2 on t1.salary <=t2.salary

ID	SALARY	ID	SALARY
1	33	1	33
1	33	2	44
1	33	3	99
2	44	2	44
2	44	3	99
3	99	3	99
--using correlated sub-query for  dense rank only.
-- cons: Slow, because the inner query will run for every row processed by the outer query.
--  100, 300, 300, 700, 700
--  1     2    2    3    3    dense rank
--  1     2    2    4    4    rank
select id,  salary from test order by salary
ID	SALARY
1	33
1	33
1	35
2	44
3	99
4	99
select id,  salary from test a where 1=(select count(distinct salary) from test b where b.salary > a.salary)
ID	SALARY
2	44

select id,  salary ,
 row_number() over(order by salary desc) as rown,
 rank() over(order by salary desc) as rank,
 dense_rank() over(order by salary desc) as denserank
from test order by salary

ID	SALARY	ROWN	RANK	DENSERANK
1	33	5	5	4
1	33	6	5	4
1	35	4	4	3
2	44	3	3	2
4	99	1	1	1
3	99	2	1	1