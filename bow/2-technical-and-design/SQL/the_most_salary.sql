-- assume we have a table EMPLOYEE <employee_id (Number), employe_salary (Number)>,
-- write a query to find out the employee who earns the most salary
--
-- assume using oracle
-- <a herf="https://www.tutorialspoint.com/oracle_terminal_online.php"> oracle terminal </a>
--
SQL> select id, salary from test where salary in ( select max(salary) from test);

        ID     SALARY
---------- ----------
         3         99
SQL> select id, salary from test where salary=( select max(salary) from test);

        ID     SALARY
---------- ----------
         3         99


SQL> select id, salary from ( select id, salary from test order by salary desc) where rownum =1;

        ID     SALARY
---------- ----------
         3         99



--without using sql max() function/s or order by operation.

SQL> select  * from test;

         ID     SALARY
 ---------- ----------
          1         33
          2         44
          3         99

--there is  not 'as' in Oracle
-- The First solution: using not in
SQL> select  * from test where id not in (select A.id from test A inner join test B on  A.salary<B.salary)；

 ID	SALARY
 3	99

SQL>  select * from test A inner join test B on  A.salary<B.salary
ID	SALARY	ID	SALARY
1	33	2	44
1	33	3	99
2	44	3	99
-- The second solution: using count()
SQL>  select * from test where id = (select id from (
  select t1.id, count(1) as c from test t1, test t2 where t1.salary <=t2.salary group by t1.id)
where c=1)

SQL>  select t1.id, count(1) as c from test t1, test t2 where t1.salary <=t2.salary group by t1.id
ID	C
1	3
2	2
3	1

select a.id, a.salary, count(1) over (partition by a.id) as c from test a, test b where a.id<= b.id
ID	SALARY	C
1	33	3
1	33	3
1	33	3
2	44	2
2	44	2
3	99	1
-- The third solution: using  not exists
select id, salary from test a where not exists ( select 1 from test b where b.salary> a.salary )

--Last solution using cursor
--cons: can only find the fist max one
--https://www.tutorialspoint.com/plsql/index.htm
declare
 v_id   test.id%type;
 v_maxSalary   test.salary%type  := 0;
 v_tmp_id   test.id%type;
 v_tmp_maxSalary   test.salary%type;
 cv SYS_REFCURSOR;
BEGIN
   OPEN cv FOR
     SELECT  id, salary
     FROM test;
   LOOP
      fetch cv into v_tmp_id  , v_tmp_maxSalary ;
      IF v_tmp_maxSalary> v_maxSalary THEN
         v_maxSalary:=v_tmp_maxSalary;
         v_id:=v_tmp_id;
      END IF;
      EXIT WHEN cv%NOTFOUND;
   END LOOP;
   DBMS_OUTPUT.PUT_LINE(   v_id ||', '||  v_maxSalary );
END;
/
--OR
declare
 v_id   test.id%type;
 v_maxSalary   test.salary%type  := 0;
 cursor cv is  SELECT  id, salary  FROM test;
BEGIN
   for i in cv LOOP
      IF i.salary> v_maxSalary THEN
         v_maxSalary:=i.salary;
         v_id:=i.id;
      END IF;

   END LOOP;
   DBMS_OUTPUT.PUT_LINE(   v_id ||', '||  v_maxSalary );
END;
/
