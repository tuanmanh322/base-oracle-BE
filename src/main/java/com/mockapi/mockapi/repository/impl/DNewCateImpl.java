package com.mockapi.mockapi.repository.impl;

import com.mockapi.mockapi.repository.CateNewsDAO;
import com.mockapi.mockapi.util.HibernateUtil;
import com.mockapi.mockapi.util.PageBuilder;
import com.mockapi.mockapi.util.SQLBuilder;
import com.mockapi.mockapi.web.dto.response.resp.NewsResponse;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class DNewCateImpl implements CateNewsDAO {
    private static final Logger log = LoggerFactory.getLogger(DNewCateImpl.class);


    @Override
    public List<NewsResponse> getAllById(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_NEWS_CATE, "getNewByCate"));
            SQLQuery query = session.createSQLQuery(sb.toString());
            query.setParameter(1, id);
            query.addScalar("id", new LongType());
            query.addScalar("content", new StringType());
            query.addScalar("posted", new BooleanType());
            query.addScalar("summary", new StringType());
            query.addScalar("thumbnail", new StringType());
            query.addScalar("timePost", new DateType());
            query.addScalar("title", new StringType());
            query.addScalar("username", new StringType());
            query.addScalar("name", new StringType());
            List<NewsResponse> list = query.getResultList();
            return list;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    private int count() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        int count = 0;
        try {
            String sql = SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_NEWS, "countNews");
            SQLQuery query = session.createSQLQuery(sql);
            count = query.list().size();
            return count;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            session.close();
        }

        return count;
    }
}
