package com.mockapi.mockapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "POSITION")
//@Table
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "POSITION_ID_SEQ")
    @SequenceGenerator(name = "POSITION_ID_SEQ",sequenceName = "AUTO_INCRE_SEQ_POSITION",initialValue = 1,allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "position",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference(value = "employee-position")
    private List<Employee> employees;
}
