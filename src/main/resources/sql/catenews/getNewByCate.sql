select n.id id, n.content content,n.posted posted, n.summary summary,n.thumbnail thumbnail,n.time_post timePost,n.title title,
e.username username,
nc.name name
from news n
inner join employee e on e.id = n.employee_id
INNER join news_category nc on  nc.id = n.newscategory_id
where  n.posted = 1 and nc.id =?1
