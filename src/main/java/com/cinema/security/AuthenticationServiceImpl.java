package com.cinema.security;

import com.cinema.model.Role;
import com.cinema.model.User;
import com.cinema.service.RoleService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = Logger.getLogger(AuthenticationServiceImpl.class);
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final RoleService roleService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService,
                                     RoleService roleService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.roleService = roleService;
    }

    @Override
    public User register(String email, String password) {
        logger.info("Trying to register user with email " + email);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Role userRole = roleService.getRoleByName("USER");
        user.setRoles((Set.of(userRole)));
        User newUser = userService.add(user);
        shoppingCartService.registerNewShoppingCart(newUser);
        logger.info("Register new user with email " + email + " successfully");
        return newUser;
    }
}

