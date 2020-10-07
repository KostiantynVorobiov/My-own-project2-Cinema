package com.cinema.security;

import com.cinema.exeption.AuthenticationException;
import com.cinema.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password);
}
