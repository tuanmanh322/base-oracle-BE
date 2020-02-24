package com.mockapi.mockapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TEAM")
//@Table
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "TEAM_ID_SEQ")
    @SequenceGenerator(name = "TEAM_ID_SEQ",sequenceName = "AUTO_INCRE_SEQ_TEAM",initialValue = 1,allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Column(name = "LEADER_ID")
    private int leaderId;

    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@JsonManagedReference(value = "teamp-team")
    private List<TeamProject> teamProjects;

    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference(value = "employee-team")
    private List<Employee> employees;
}
