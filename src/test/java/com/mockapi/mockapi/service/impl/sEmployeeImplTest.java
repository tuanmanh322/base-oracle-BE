package com.mockapi.mockapi.service.impl;

import com.mockapi.mockapi.model.Employee;
import com.mockapi.mockapi.repository.EmployeeRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


class sEmployeeImplTest {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Test
    void findByUsername() {
      Employee employee= employeeRepo.findByUsername("test");
    }



    @Test
    void testFindById() {
       // Employee e = employeeRepo.findById();
    }
}
