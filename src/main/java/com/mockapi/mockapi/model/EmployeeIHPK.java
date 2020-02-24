package com.mockapi.mockapi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Getter
@Setter
@Embeddable
public class EmployeeIHPK implements Serializable {
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "ISSUES_ID")
    private Long issueId;


}
