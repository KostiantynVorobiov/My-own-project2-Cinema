package com.cinema.dao.impl;

import com.cinema.dao.TicketDao;
import com.cinema.exeption.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.Ticket;
import com.cinema.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class TicketDaoImpl implements TicketDao {
    private static final Logger logger = Logger.getLogger(TicketDaoImpl.class);

    @Override
    public Ticket add(Ticket ticket) {
        logger.info("Trying to add ticket");
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
            logger.info("Added new ticket " + ticket + " successfully");
            return ticket;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add ticket with id " + ticket.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
