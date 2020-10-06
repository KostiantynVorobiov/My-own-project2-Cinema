package com.cinema.service.impl;

import com.cinema.dao.CinemaHallDao;
import com.cinema.lib.Inject;
import com.cinema.model.CinemaHall;
import com.cinema.service.CinemaHallService;

import java.util.List;

public class CinemaHallServiceImpl implements CinemaHallService {

    @Inject
    private CinemaHallDao cinemaHallDao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
