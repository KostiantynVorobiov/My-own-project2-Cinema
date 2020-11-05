package com.cinema.util;

import com.cinema.model.Role;
import com.cinema.model.User;
import com.cinema.service.RoleService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInjector {
    private final UserService userService;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public DataInjector(UserService userService,
                        RoleService roleService,
                        ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostConstruct
    public void addRolesAndAdmin() {
        Role adminRole = Role.of("ADMIN");
        roleService.add(adminRole);
        Role userRole = Role.of("USER");
        roleService.add(userRole);
        User admin = new User();
        admin.setEmail("admin@i.ua");
        admin.setPassword("admin");
        admin.setRoles(Set.of(adminRole));
        userService.add(admin);
    }
}
