package com.mockapi.mockapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "ROLE")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ROLE_ID_SEQ")
    @SequenceGenerator(name = "ROLE_ID_SEQ",sequenceName = "AUTO_INCRE_SEQ_ROLE",initialValue = 1,allocationSize = 1)
    @Column(name = "ID")
    private long id;

    @Column(name = "ROLE_NAME")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees  = new HashSet<>();

    @Override
    public String getAuthority() {
        return name;
    }


    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @JsonIgnore
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
