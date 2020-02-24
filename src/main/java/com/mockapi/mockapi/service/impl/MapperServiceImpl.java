package com.mockapi.mockapi.service.impl;

import com.mockapi.mockapi.model.*;
import com.mockapi.mockapi.service.ISMapperService;
import com.mockapi.mockapi.web.dto.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperServiceImpl implements ISMapperService {
    private static final Logger log = LoggerFactory.getLogger(MapperServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Employee mapEmployeeDTOToEmployee(EmployeeDTO dto) {

        return null;
    }

    @Override
    public EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee) {
        return null;
    }

    @Override
    public News mapNewsDTOToNews(NewsDTO dto) {
        return null;
    }

    @Override
    public NewsDTO mapNewsToNewsDTO(News news) {
        return null;
    }

    @Override
    public Issues mapIssuesDTOToIssues(IssueDTO dto) {
        Issues issues = modelMapper.map(dto,Issues.class);
        if(issues.getEmployee_issues() !=null){
            List<Employee_Issue> employeeIssues = new ArrayList<>();
            dto.getEmployee_issues().stream().map((employeeIssue)-> modelMapper.map(employeeIssue,Employee_Issue.class)).forEachOrdered((empIssues)->{
                employeeIssues.add(empIssues);
            });
            issues.setEmployee_issues(employeeIssues);
        }
        if(issues.getIssuesHistories() !=null){
            List<Issues_History> issuesHistories = new ArrayList<>();
            dto.getIssuesHistories().stream().map((issuesHistory)-> modelMapper.map(issuesHistory,Issues_History.class)).forEachOrdered((issHistory)->{
                issuesHistories.add(issHistory);
            });
            issues.setIssuesHistories(issuesHistories);
        }
        return issues;
    }

    @Override
    public IssueDTO mapIssuesToIssuesDTO(Issues issues) {
        IssueDTO dto = modelMapper.map(issues, IssueDTO.class);
        if(dto.getEmployee_issues() != null){
            List<Employee_IssueDTO> employeeIssues = new ArrayList<>();
            dto.getEmployee_issues().stream().map((empI)-> modelMapper.map(empI,Employee_IssueDTO.class))
                    .forEachOrdered((empIssue)->{
                employeeIssues.add(empIssue);
            });
            dto.setEmployee_issues(employeeIssues);
        }
        if(dto.getIssuesHistories() != null){
            List<Issues_HistoryDTO> issues_historyDTOS = new ArrayList<>();
            dto.getIssuesHistories().stream().map((issueHi)-> modelMapper.map(issueHi,Issues_HistoryDTO.class))
                    .forEachOrdered((issueH)->{
                issues_historyDTOS.add(issueH);
            });
            dto.setIssuesHistories(issues_historyDTOS);
        }
        return dto;
    }
}
