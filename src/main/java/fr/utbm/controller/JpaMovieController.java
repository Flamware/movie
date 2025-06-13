package fr.utbm.controller;

import fr.utbm.entity.Movie;
import fr.utbm.services.JpaMovieService;

import java.util.List;

public class JpaMovieController {

    private JpaMovieService movieService;

    public JpaMovieController(JpaMovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Retrieves all movies from the service layer.
     *
     * @return A List of all Movie objects, or an empty list if none are found or an error occurs.
     */
    public List<Movie> getMovies() {
        // Call the service method to get movies
        return movieService.getMovies();
    }


}
