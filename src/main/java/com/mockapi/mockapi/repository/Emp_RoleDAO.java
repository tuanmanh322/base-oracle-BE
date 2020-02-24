package com.mockapi.mockapi.repository;

public interface Emp_RoleDAO {
    void setRole(long empId,long roleID);

    void deleteRoleEmp(Long id);
}
