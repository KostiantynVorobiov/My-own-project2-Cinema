package com.cinema.dao.impl;

import com.cinema.dao.CinemaHallDao;
import com.cinema.exeption.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.CinemaHall;
import com.cinema.util.HibernateUtil;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final Logger logger = Logger.getLogger(CinemaHallDaoImpl.class);

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        logger.info("Trying to add cinema hall");
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
            logger.info("Added new cinema hall" + cinemaHall + "successfully");
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
    public List<CinemaHall> getAll() {
        logger.info("Trying to get all cinema halls");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CinemaHall> getAllCinemaHallQuery
                    = session.createQuery("FROM CinemaHall", CinemaHall.class);
            return getAllCinemaHallQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't show all cinema halls", e);
        }
    }
}
