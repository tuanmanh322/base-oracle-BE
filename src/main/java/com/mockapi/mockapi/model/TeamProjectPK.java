package com.mockapi.mockapi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TeamProjectPK implements Serializable {
    @Column(name = "PROJECT_ID")
    private Long idProject;

    @Column(name = "TEAM_ID")
    private Long idTeam;
}
