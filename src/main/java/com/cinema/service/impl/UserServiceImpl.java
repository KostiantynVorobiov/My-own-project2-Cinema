package com.cinema.service.impl;

import com.cinema.dao.UserDao;
import com.cinema.lib.Inject;
import com.cinema.lib.Service;
import com.cinema.model.User;
import com.cinema.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
