package com.mockapi.mockapi.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mockapi.mockapi.model.Department;
import com.mockapi.mockapi.model.Position;
import com.mockapi.mockapi.model.Team;
import com.mockapi.mockapi.web.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SearchEmployeeRequest extends BaseDTO{
    private Long id;

    private String username;

    private String email;

    private Date createdDate;

    private String fullName;

    private boolean isActived;

    private Date lastAccess;

    private int phoneNumber;

    private String userType;

    private String roleName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    private String sortAtoZ;

    private String sortZtoA;
    @Override
    public String toString() {
        return "SearchEmployeeRequest{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                ", fullName='" + fullName + '\'' +
                ", isActived=" + isActived +
                ", lastAccess=" + lastAccess +
                ", phoneNumber=" + phoneNumber +
                ", userType='" + userType + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
