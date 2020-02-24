package com.mockapi.mockapi.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mockapi.mockapi.model.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EmployeeEditRequest implements Serializable {
    private long id;


    private String username;

    @Email(message = "email can't be null")
    private String email;

    //@NotNull(message = "birthday can't be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotNull(message = "address can't be null")
    private String address;

    //@NotNull(message = "createDate can't be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @NotNull(message = "education can't be null")
    private String education;

    @NotNull(message = "faculity can't be null")
    private String faculty;

    @NotNull(message = "facebook can't be null")
    private String fbLink;

    @NotNull(message = "fullName can't be null")
    private String fullName;

    @NotNull(message = "GraduatedYear can't be null")
    private int graduationYear;


    private String image;

    private boolean isActived;

    private boolean isLeader;

    private boolean isManager;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastAccess;

    @NotNull(message = "phoneNumber can't be null")
    private int phoneNumber;

    @NotNull(message = "skypeAcc can't be null")
    private String skypeAcc;

    @NotNull(message = "university can't be null")
    private String university;

    @NotNull(message = "userType can't be null")
    private String userType;

    private List<News> news;

    private List<ABSENT> absents;

    private List<Issues_History> issues_histories;

    private List<String> authorities;

    private EmployeeToken employeeToken;

    private Department department;

    private Position position;

}
