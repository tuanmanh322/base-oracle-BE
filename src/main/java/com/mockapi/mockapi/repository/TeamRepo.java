package com.mockapi.mockapi.repository;

import com.mockapi.mockapi.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepo extends JpaRepository<Team,Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM TEAM T WHERE T.ID =?1")
    Team findByIdLeader(long id);
}
