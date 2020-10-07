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
        User alcapone = new User();
        alcapone.setEmail("alcaponchik@porishau.com");
        alcapone.setPassword("777");
        userService.add(alcapone);
        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User mclane = new User();
        mclane.setEmail("oreshek@cop.com");
        mclane.setPassword("911");
        authenticationService.register(mclane.getEmail(), mclane.getPassword());
        authenticationService.login(mclane.getEmail(), mclane.getPassword());
        System.out.println(userService.findByEmail(mclane.getEmail()).get());
    }
}
