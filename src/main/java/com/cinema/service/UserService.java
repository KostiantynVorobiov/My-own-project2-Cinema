package com.cinema.service;

import com.cinema.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    User findByEmail(String email);
}
