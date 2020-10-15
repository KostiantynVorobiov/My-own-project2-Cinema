package com.cinema.dao.impl;

import com.cinema.dao.MovieSessionDao;
import com.cinema.exeption.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.MovieSession;
import com.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger logger = Logger.getLogger(MovieSessionDaoImpl.class);

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        logger.info("Trying to find available session");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getAllMovieSessionQuery
                    = session.createQuery("FROM MovieSession WHERE movie.id = :id "
                    + "AND showTime BETWEEN :start AND :end", MovieSession.class);
            getAllMovieSessionQuery.setParameter("id", movieId);
            getAllMovieSessionQuery.setParameter("start", date.atTime(LocalTime.MIN));
            getAllMovieSessionQuery.setParameter("end", date.atTime(LocalTime.MAX));
            return getAllMovieSessionQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movie session with movieId "
                    + movieId + " and " + date.toString(), e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        logger.info("Trying to add movie session");
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            logger.info("Added new movie session" + movieSession + " successfully");
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie session with id "
                    + movieSession.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
