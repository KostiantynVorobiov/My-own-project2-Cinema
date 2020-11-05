package com.cinema.security;

import com.cinema.model.User;
import com.cinema.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user in Db by email: "
                        + email));
        UserBuilder builder = null;
        builder = org.springframework.security.core.userdetails.User.withUsername(email);
        builder.password(user.getPassword());
        String[] rolesArray = user.getRoles().stream()
                .map(role -> role.getRoleName().toString())
                .toArray(String[]::new);
        builder.roles(rolesArray);
        return builder.build();
    }
}
