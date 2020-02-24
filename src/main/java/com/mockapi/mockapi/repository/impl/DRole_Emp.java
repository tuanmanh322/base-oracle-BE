package com.mockapi.mockapi.repository.impl;

import com.mockapi.mockapi.repository.Emp_RoleDAO;
import com.mockapi.mockapi.util.HibernateUtil;
import com.mockapi.mockapi.util.SQLBuilder;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DRole_Emp implements Emp_RoleDAO {
    private static final Logger log = LoggerFactory.getLogger(DRole_Emp.class);

    @Override
    public void setRole(long empId, long roleID) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
    }

    @Override
    public void deleteRoleEmp(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            StringBuilder sb = new StringBuilder();
            sb.append(SQLBuilder.getSqlQueryById(SQLBuilder.SQL_MODULE_EMP_ROLE,"deleteRow"));
            SQLQuery query = session.createSQLQuery(sb.toString());
            query.setParameter("p_id",id);
            query.addScalar("id", new LongType());
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            tx.rollback();
        }finally {
            session.close();
        }
    }
}
