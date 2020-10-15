package com.cinema.dao.impl;

import com.cinema.dao.OrderDao;
import com.cinema.exeption.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.Order;
import com.cinema.model.User;
import com.cinema.util.HibernateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public Order add(Order order) {
        logger.info("Trying to add order");
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            logger.info("Added new order " + order + " successfully");
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add order by order id: " + order.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        logger.info("Trying to get order history");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Order> getOrdersQuery = session.createQuery("SELECT DISTINCT o FROM Order o "
                    + "LEFT JOIN FETCH o.tickets "
                    + "WHERE o.user = : user", Order.class);
            getOrdersQuery.setParameter("user", user);
            return getOrdersQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all orders by user: "
                    + user.getEmail(), e);
        }
    }
}

