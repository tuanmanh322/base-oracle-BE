SELECT COUNT(1) FROM news n
inner join employee e on e.id = n.employee_id
INNER join news_category nc on  nc.id = n.newscategory_id
where 1 = 1
