package com.cinema;

import com.cinema.dao.impl.CinemaHallDaoImpl;
import com.cinema.exeption.AuthenticationException;
import com.cinema.lib.Injector;
import com.cinema.model.CinemaHall;
import com.cinema.model.Movie;
import com.cinema.model.MovieSession;
import com.cinema.model.User;
import com.cinema.security.AuthenticationService;
import com.cinema.service.CinemaHallService;
import com.cinema.service.MovieService;
import com.cinema.service.MovieSessionService;
import com.cinema.service.OrderService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie furious = new Movie();
        movieService.getAll().forEach(logger::info);
        furious.setTitle("Fast and Furious");
        furious.setDescription("Action movie about cars, races and fights");
        movieService.add(furious);
        Movie cars = new Movie();
        cars.setTitle("Cars");
        cars.setDescription("Cartoon for children");
        movieService.add(cars);
        logger.info("All movies");
        movieService.getAll().forEach(logger::info);

        CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHallHorror = new CinemaHall();
        cinemaHallHorror.setCapacity(50);
        cinemaHallHorror.setDescription("for horror");
        cinemaHallService.add(cinemaHallHorror);
        CinemaHall cinemaHallFamily = new CinemaHall();
        cinemaHallFamily.setCapacity(100);
        cinemaHallFamily.setDescription("for family");
        cinemaHallService.add(cinemaHallFamily);
        cinemaHallService.getAll().forEach(logger::info);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setCinemaHall(cinemaHallHorror);
        movieSession1.setMovie(furious);
        movieSession1.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.findAvailableSessions(furious.getId(),
                LocalDate.now()).forEach(logger::info);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setCinemaHall(cinemaHallFamily);
        movieSession2.setMovie(cars);
        movieSession2.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(movieSession2);
        movieSessionService.findAvailableSessions(cars.getId(),
                LocalDate.now().plusDays(1)).forEach(logger::info);

        User panas = new User();
        panas.setEmail("panas@u.com");
        panas.setPassword("777");

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User onic = new User();
        onic.setEmail("onic@u.com");
        onic.setPassword("911");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        authenticationService.register(onic.getEmail(), onic.getPassword());
 //       authenticationService.login(onic.getEmail(), onic.getPassword());
        logger.info("User by email " + userService.findByEmail(onic.getEmail()).get());
        User foundUser = userService.findByEmail("onic@u.com").get();
        logger.info("My found user: " + foundUser);

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        authenticationService.register("emily@com", "12345");
        User emily = null;
        try {
            emily = authenticationService.login("emily@com", "12345");
        } catch (AuthenticationException e) {
            logger.error(e);
        }
        shoppingCartService.addSession(movieSession1, emily);
        logger.info("Get cart by Emily user: " + shoppingCartService.getByUser(emily));
        shoppingCartService.clear(shoppingCartService.getByUser(emily));
        logger.info("Get cart by Emily user after clear: "
                + shoppingCartService.getByUser(emily));
        shoppingCartService.registerNewShoppingCart(panas);
        shoppingCartService.addSession(movieSession1, panas);
        shoppingCartService.addSession(movieSession2, foundUser);
        logger.info("Get cart by Panas user: " + shoppingCartService.getByUser(panas));
        shoppingCartService.clear(shoppingCartService.getByUser(panas));
        logger.info("Get cart by Panas user after clear: "
                + shoppingCartService.getByUser(panas));

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCartService.getByUser(foundUser).getTickets(),
                foundUser);

        logger.info("All orders in found user he is Onic"
                + orderService.getOrderHistory(foundUser));
        orderService.getOrderHistory(foundUser).forEach(logger::info);
    }
}
