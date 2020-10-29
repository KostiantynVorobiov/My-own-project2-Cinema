package com.cinema.security;

import com.cinema.model.User;

public interface AuthenticationService {

    User register(String email, String password);
}
