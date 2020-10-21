package com.cinema.dao.impl;

import com.cinema.dao.ShoppingCartDao;
import com.cinema.exeption.DataProcessingException;
import com.cinema.model.ShoppingCart;
import com.cinema.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private static final Logger logger = Logger.getLogger(ShoppingCartDaoImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public ShoppingCartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        logger.info("Trying to add shopping cart");
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            logger.info("Added new shopping cart " + shoppingCart + " successfully");
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add shopping cart with id "
                    + shoppingCart.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        logger.info("Trying to get shopping cart by user");
        try (Session session = sessionFactory.openSession()) {
            Query<ShoppingCart> getCartByUserQuery
                    = session.createQuery("FROM ShoppingCart sc "
                    + "JOIN FETCH sc.user LEFT JOIN FETCH sc.tickets "
                    + "WHERE sc.user = : user", ShoppingCart.class);
            getCartByUserQuery.setParameter("user", user);
            return getCartByUserQuery.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get shopping cart by user "
                    + user.getEmail(), e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        logger.info("Trying to update shopping cart");
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            transaction.commit();
            logger.info("Updated shopping cart " + shoppingCart + " successfully");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update shopping cart with id "
                    + shoppingCart.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
