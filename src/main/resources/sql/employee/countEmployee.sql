SELECT COUNT(1) FROM employee e
         inner join employee_role er on(er.employee_id = e.id)
         inner join role r on(er.role_id = r.id)
         INNER JOIN department d on(e.department_id = d.id)
         INNER join team t on(t.id = e.team_id)
where 1 = 1
