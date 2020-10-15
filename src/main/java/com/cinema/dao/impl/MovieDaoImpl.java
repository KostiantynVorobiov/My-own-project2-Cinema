package com.cinema.dao.impl;

import com.cinema.dao.MovieDao;
import com.cinema.exeption.DataProcessingException;
import com.cinema.lib.Dao;
import com.cinema.model.Movie;
import com.cinema.util.HibernateUtil;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieDaoImpl implements MovieDao {
    private static final Logger logger = Logger.getLogger(MovieDaoImpl.class);
    @Override
    public Movie add(Movie movie) {
        logger.info("Trying to add movie");
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movie);
            transaction.commit();
            return movie;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add movie " + movie.getTitle(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Movie> getAll() {
        logger.info("Trying to get all movies");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Movie> getAllMoviesQuery = session.createQuery("FROM Movie", Movie.class);
            return getAllMoviesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all movies", e);
        }
    }
}
