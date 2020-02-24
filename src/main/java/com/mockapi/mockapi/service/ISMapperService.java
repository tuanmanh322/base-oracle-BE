package com.mockapi.mockapi.service;

import com.mockapi.mockapi.model.Employee;
import com.mockapi.mockapi.model.Issues;
import com.mockapi.mockapi.model.News;
import com.mockapi.mockapi.web.dto.EmployeeDTO;
import com.mockapi.mockapi.web.dto.IssueDTO;
import com.mockapi.mockapi.web.dto.NewsDTO;

public interface ISMapperService {
    Employee mapEmployeeDTOToEmployee(EmployeeDTO dto);
    EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee);

    News mapNewsDTOToNews(NewsDTO dto);
    NewsDTO mapNewsToNewsDTO(News news);

    Issues mapIssuesDTOToIssues(IssueDTO dto);
    IssueDTO mapIssuesToIssuesDTO(Issues issues);

}
