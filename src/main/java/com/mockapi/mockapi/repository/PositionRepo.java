package com.mockapi.mockapi.repository;

import com.mockapi.mockapi.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepo extends JpaRepository<Position,Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM POSITION P WHERE P.NAME=?1")
    Position findByName(String namePosition);
}
