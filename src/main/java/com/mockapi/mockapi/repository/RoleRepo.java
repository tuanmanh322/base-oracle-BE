package com.mockapi.mockapi.repository;

import com.mockapi.mockapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM ROLE R WHERE R.ROLE_NAME=?1")
    Role findByName(String role);
}
