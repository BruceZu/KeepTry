--oracle left join
select * from test a,test b  where a.salary <b.salary(+)
select * from test a left join test b  on a.salary <b.salary
ID	SALARY	ID	SALARY
1	33	2	44
1	33	3	99
2	44	3	99
3	99	-	-
