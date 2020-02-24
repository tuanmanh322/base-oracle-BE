package com.mockapi.mockapi.repository;

import com.mockapi.mockapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    Employee findByUsername(String username);

    @Query(nativeQuery = true,value = "UPDATE EMPLOYEE  SET IS_ACTIVED=?1 ")
    void activeAcc(int type);

    @Query(nativeQuery = true,value = "SELECT e.USERNAME from EMPLOYEE e inner Join NEWS N on e.ID = N.EMPLOYEE_ID where N.EMPLOYEE_ID=?1")
    Employee findByUsernameByNewId(Long id);


    @Query(nativeQuery = true, value = "SELECT * FROM EMPLOYEE E WHERE E.EMAIL=?1 AND rownum = 1")
    Employee findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM EMPLOYEE E WHERE E.EMAIL=?1")
    Employee checkEmail(String email);
}
