package com.mockapi.mockapi.repository;

import com.mockapi.mockapi.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepo extends JpaRepository<News,Long> {
    @Query(nativeQuery = true,value = "select * from NEWS n where n.EMPLOYEE_ID=?1 AND rownum = 1")
    News findByEmployeeId(Long id);

    @Query(nativeQuery = true,value = "select * from NEWS n where n.NEWSCATEGORY_ID=?1")
    List<News> findByNewsCategoryId(Long id);
}
