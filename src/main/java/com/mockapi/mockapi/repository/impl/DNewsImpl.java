package com.mockapi.mockapi.repository.impl;

import com.mockapi.mockapi.repository.NewsDao;
import com.mockapi.mockapi.util.DataUtils;
import com.mockapi.mockapi.util.HibernateUtil;
import com.mockapi.mockapi.util.PageBuilder;
import com.mockapi.mockapi.util.SQLBuilder;
import com.mockapi.mockapi.web.dto.request.SearchNewsRequest;
import com.mockapi.mockapi.web.dto.response.resp.NewsResponse;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DNewsImpl implements NewsDao {
    private Logger log = LoggerFactory.getLogger(DNewsImpl.class);

    @Override
    public Page<NewsResponse> getAll(SearchNewsRequest request) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_NEWS,"getAllNews"));
            if(!DataUtils.isNullOrEmpty(request.getTitle())){
                sb.append(" AND n.title LIKE :p_title");
            }
            if(!DataUtils.isNullOrEmpty(request.getUsername())){
                sb.append(" AND e.username LIKE :p_username");
            }

            SQLQuery query = session.createSQLQuery(sb.toString());
            if (!DataUtils.isNullOrEmpty(request.getTitle())) {
                query.setParameter("p_title", "%" +
                        request.getTitle().trim()
                                .replace("\\", "\\\\")
                                .replaceAll("%", "\\%")
                                .replaceAll("_", "\\_")
                        + "%");
            }
            if (!DataUtils.isNullOrEmpty(request.getUsername())) {
                query.setParameter("p_username", "%" +
                        request.getUsername().trim()
                                .replace("\\", "\\\\")
                                .replaceAll("%", "\\%")
                                .replaceAll("_", "\\_")
                        + "%");
            }
            query.addScalar("id",new LongType());
            query.addScalar("content",new StringType());
            query.addScalar("posted",new BooleanType());
            query.addScalar("summary",new StringType());
            query.addScalar("thumbnail",new StringType());
            query.addScalar("timePost",new DateType());
            query.addScalar("title",new StringType());
            query.addScalar("username",new StringType());
            query.addScalar("name",new StringType());

            query.setResultTransformer(Transformers.aliasToBean(SearchNewsRequest.class));
            int count = 0;

            if (request.getPage() != null && request.getPageSize() != null) {
                Pageable pageable = PageBuilder.buildPageable(request);
                if (pageable != null) {
                    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
                    query.setMaxResults(pageable.getPageSize());
                }
                List<NewsResponse> data = query.list();

                Page<NewsResponse> dataPage = new PageImpl<>(data, pageable, count());
                return dataPage;
            }
            tx.commit();
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            if(tx !=null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Page<NewsResponse> getAllByAd(SearchNewsRequest request) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_NEWS,"getAllNews"));
            if(!DataUtils.isNullOrEmpty(request.getTitle())){
                sb.append(" AND n.title LIKE :p_title");
            }
            if(!DataUtils.isNullOrEmpty(request.getUsername())){
                sb.append(" AND e.username LIKE :p_username");
            }

            SQLQuery query = session.createSQLQuery(sb.toString());
            if (!DataUtils.isNullOrEmpty(request.getTitle())) {
                query.setParameter("p_title", "%" +
                        request.getTitle().trim()
                                .replace("\\", "\\\\")
                                .replaceAll("%", "\\%")
                                .replaceAll("_", "\\_")
                        + "%");
            }
            if (!DataUtils.isNullOrEmpty(request.getUsername())) {
                query.setParameter("p_username", "%" +
                        request.getUsername().trim()
                                .replace("\\", "\\\\")
                                .replaceAll("%", "\\%")
                                .replaceAll("_", "\\_")
                        + "%");
            }
            query.addScalar("id",new LongType());
            query.addScalar("content",new StringType());
            query.addScalar("posted",new BooleanType());
            query.addScalar("summary",new StringType());
            query.addScalar("thumbnail",new StringType());
            query.addScalar("timePost",new DateType());
            query.addScalar("title",new StringType());
            query.addScalar("username",new StringType());
            query.addScalar("name",new StringType());

            query.setResultTransformer(Transformers.aliasToBean(SearchNewsRequest.class));
            int count = 0;

            if (request.getPage() != null && request.getPageSize() != null) {
                Pageable pageable = PageBuilder.buildPageable(request);
                if (pageable != null) {
                    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
                    query.setMaxResults(pageable.getPageSize());
                }
                List<NewsResponse> data = query.list();

                Page<NewsResponse> dataPage = new PageImpl<>(data, pageable, count());
                return dataPage;
            }
            tx.commit();
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            if(tx !=null){
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return null;
    }

    private int count(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        int count = 0;
        try {
            String sql = SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_NEWS,"countNews");
            SQLQuery query = session.createSQLQuery(sql);
            count = query.list().size();
            return count;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }finally {
            session.close();
        }

        return count;
    }
}
