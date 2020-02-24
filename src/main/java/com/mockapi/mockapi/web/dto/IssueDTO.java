package com.mockapi.mockapi.web.dto;

import com.mockapi.mockapi.model.Employee_Issue;
import com.mockapi.mockapi.model.Issues_History;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO extends BaseDTO {
    private Long id;

    private String name;

    private Date startDate;

    private Date dueDate;

    private int donePersent;

    private String priority;

    private String reason;

    private String description;

    private String type;

    private List<Employee_IssueDTO> employee_issues;

    private List<Issues_HistoryDTO> issuesHistories;

    private Long statusId;

    private Long projectId;
}
