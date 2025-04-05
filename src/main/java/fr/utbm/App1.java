package fr.utbm;

import fr.utbm.services.MovieService;
import fr.utbm.controller.DefaultMovieController;
import fr.utbm.entity.Movie;

public class App1 {
    public static void main(String[] args) {
        DefaultMovieController controller = new DefaultMovieController();
        Movie MonFilm = controller.registerMovieFromConsoleInput();
        MovieService mService = new MovieService();
        mService.registerMovie(MonFilm);
    }
}