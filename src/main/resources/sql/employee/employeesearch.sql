select e.id id,e.username username,e.email email,e.created_date createdDate,e.fullname fullName,e.is_actived isActived,
       e.last_access lastAccess,e.phone_number phoneNumber,e.user_type userType,
       r.role_name roleName

from employee e
         inner join employee_role er on(er.employee_id = e.id)
         inner join role r on(er.role_id = r.id)
where 1 = 1
