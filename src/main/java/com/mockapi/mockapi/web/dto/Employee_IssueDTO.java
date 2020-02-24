package com.mockapi.mockapi.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Getter
@Setter
@Log4j
public class Employee_IssueDTO extends BaseDTO{
    private long id;

    private float spentTime;

    private String note;
}
