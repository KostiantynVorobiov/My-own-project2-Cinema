package com.cinema.dao;

import com.cinema.model.CinemaHall;
import java.util.List;

public interface CinemaHallDao {
    CinemaHall add(CinemaHall cinemaHall);

    CinemaHall getById(Long id);

    List<CinemaHall> getAll();
}
