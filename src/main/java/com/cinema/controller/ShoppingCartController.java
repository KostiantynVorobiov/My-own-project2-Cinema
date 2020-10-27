package com.cinema.controller;

import com.cinema.model.MovieSession;
import com.cinema.model.ShoppingCart;
import com.cinema.model.User;
import com.cinema.model.dto.ShoppingCartResponseDto;
import com.cinema.service.MovieSessionService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import com.cinema.service.mapper.MovieSessionMapper;
import com.cinema.service.mapper.ShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final MovieSessionService movieSessionService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final MovieSessionMapper movieSessionMapper;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService,
                                  MovieSessionService movieSessionService,
                                  ShoppingCartMapper shoppingCartMapper,
                                  MovieSessionMapper movieSessionMapper) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.movieSessionService = movieSessionService;
        this.shoppingCartMapper = shoppingCartMapper;
        this.movieSessionMapper = movieSessionMapper;
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getByUser(@RequestParam String email) {
        User user = userService.findByEmail(email);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        return shoppingCartMapper.convertToResponseDto(shoppingCart);
    }

    @PostMapping("/movie-sessions")
    public String addMovieSession(@RequestParam String email, @RequestParam Long sessionId) {
        User user = userService.findByEmail(email);
        MovieSession movieSession = movieSessionService.get(sessionId);
        shoppingCartService.addSession(movieSession, user);
        return "Movie session in shopping cart added successfully";
    }
}
