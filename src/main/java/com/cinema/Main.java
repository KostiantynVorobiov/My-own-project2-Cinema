package com.cinema;

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
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");

    public static void main(String[] args) throws AuthenticationException {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie furious = new Movie();
        movieService.getAll().forEach(System.out::println);
        furious.setTitle("Fast and Furious");
        furious.setDescription("Action movie about cars, races and fights");
        movieService.add(furious);
        Movie cars = new Movie();
        cars.setTitle("Cars");
        cars.setDescription("Cartoon for children");
        movieService.add(cars);
        System.out.println("All movies");
        movieService.getAll().forEach(System.out::println);

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
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setCinemaHall(cinemaHallHorror);
        movieSession1.setMovie(furious);
        movieSession1.setShowTime(LocalDateTime.now());
        MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession1);
        movieSessionService.findAvailableSessions(furious.getId(),
                LocalDate.now()).forEach(System.out::println);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setCinemaHall(cinemaHallFamily);
        movieSession2.setMovie(cars);
        movieSession2.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(movieSession2);
        movieSessionService.findAvailableSessions(cars.getId(),
                LocalDate.now().plusDays(1)).forEach(System.out::println);

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User panas = new User();
        panas.setEmail("panas@u.com");
        panas.setPassword("777");
        userService.add(panas);

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User onic = new User();
        onic.setEmail("onic@u.com");
        onic.setPassword("911");
        authenticationService.register(onic.getEmail(), onic.getPassword());
        authenticationService.login(onic.getEmail(), onic.getPassword());
        System.out.println(userService.findByEmail(onic.getEmail()).get());
        User foundUser = userService.findByEmail("onic@u.com").get();
        System.out.println("My found user: " + foundUser);

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        authenticationService.register("emily@com", "12345");
        User emily = authenticationService.login("emily@com", "12345");
        shoppingCartService.addSession(movieSession1, emily);
        System.out.println("Get cart by Emily user: " + shoppingCartService.getByUser(emily));
        shoppingCartService.clear(shoppingCartService.getByUser(emily));
        System.out.println("Get cart by Emily user after clear: "
                + shoppingCartService.getByUser(emily));
        shoppingCartService.registerNewShoppingCart(panas);
        shoppingCartService.addSession(movieSession1, panas);
        shoppingCartService.addSession(movieSession2, foundUser);
        System.out.println("Get cart by panas user: " + shoppingCartService.getByUser(panas));
        shoppingCartService.clear(shoppingCartService.getByUser(panas));
        System.out.println("Get cart by panas user after clear: "
                + shoppingCartService.getByUser(panas));

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCartService.getByUser(foundUser).getTickets(),
                foundUser);

        System.out.println("All orders in found user he is Onic"
                + orderService.getOrderHistory(foundUser));
        orderService.getOrderHistory(foundUser).forEach(System.out::println);
    }
}
