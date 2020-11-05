package com.cinema.dao.impl;

import com.cinema.dao.RoleDao;
import com.cinema.exeption.DataProcessingException;
import com.cinema.model.Role;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    private static final Logger logger = Logger.getLogger(RoleDaoImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Role role) {
        logger.info("Trying to add role " + role.getRoleName());
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
            logger.info("Added new role " + role + " successfully");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add role " + role, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Role getRoleByName(String roleName) {
        logger.info("Trying to get role by name");
        try (Session session = sessionFactory.openSession()) {
            Query<Role> getRoleQuery =
                    session.createQuery("FROM Role r WHERE r.roleName =: roleName", Role.class);
            getRoleQuery.setParameter("roleName", Role.of(roleName).getRoleName());
            return getRoleQuery.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get role name be name " + roleName, e);
        }
    }
}
