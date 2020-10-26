package com.cinema.dao.impl;

import com.cinema.dao.CinemaHallDao;
import com.cinema.exeption.DataProcessingException;
import com.cinema.model.CinemaHall;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final Logger logger = Logger.getLogger(CinemaHallDaoImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public CinemaHallDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        logger.info("Trying to add cinema hall");
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            logger.info("Added new cinema hall " + cinemaHall + " successfully");
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add cinema hall with id "
                    + cinemaHall.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public CinemaHall get(Long id) {
        logger.info("Trying to get cinema hall by id");
        try (Session session = sessionFactory.openSession()) {
            return session.get(CinemaHall.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get cinema hall by id: " + id, e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        logger.info("Trying to get all cinema halls");
        try (Session session = sessionFactory.openSession()) {
            Query<CinemaHall> getAllCinemaHallQuery
                    = session.createQuery("FROM CinemaHall", CinemaHall.class);
            return getAllCinemaHallQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't show all cinema halls", e);
        }
    }
}
