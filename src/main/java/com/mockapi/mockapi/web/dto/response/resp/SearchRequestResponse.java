package com.mockapi.mockapi.web.dto.response.resp;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class SearchRequestResponse{
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



    @Override
    public String toString() {
        return "SearchRequestResponse{" +
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
