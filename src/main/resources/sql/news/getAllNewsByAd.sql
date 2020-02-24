select n.id id, n.content content,n.posted posted, n.summary summary,n.thumbnail,n.time_post timePost,n.title,
e.username,
nc.name
from news n
inner join employee e on e.id = n.employee_id
INNER join news_category nc on  nc.id = n.newscategory_id
where  1 = 1
