package com.mockapi.mockapi.repository;

import com.mockapi.mockapi.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Long> {
   @Query(nativeQuery = true,value = "SELECT * FROM DEPARTMENT D WHERE D.MANAGER_ID=?1")
    Department findByIdEmp(Long id);

   @Query(nativeQuery = true,value = "SELECT * FROM DEPARTMENT D WHERE D.DEPARTMENT_NAME=?1")
    Department findByName(String deName);
}
