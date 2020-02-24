package com.mockapi.mockapi.repository;

import com.mockapi.mockapi.model.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsCategoryRepo extends JpaRepository<NewsCategory, Long> {
    @Query(nativeQuery = true,value = "SELECT nc.NAME FROM  NEWS_CATEGORY nc INNER JOIN NEWS n on nc.ID = n.NEWSCATEGORY_ID WHERE n.NEWSCATEGORY_ID=?1")
    NewsCategory findNameByIdNews(Long id);


    @Query(nativeQuery = true, value = "SELECT *  FROM NEWS_CATEGORY NC WHERE NC.NAME=?1")
    NewsCategory findByName(String name);

    @Query(nativeQuery = true, value = "select  * \n" +
            "e.username,\n" +
            "nc.name\n" +
            "from news n\n" +
            "inner join employee e on e.id = n.employee_id\n" +
            "INNER join news_category nc on  nc.id = n.newscategory_id\n" +
            "where  n.posted = 1 where nc.id =?1\n ")
    NewsCategory findNewsAndEmployee(Long id);
}
