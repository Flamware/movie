package fr.utbm.services;

import fr.utbm.entity.Movie;
import fr.utbm.repository.JPAMovieDao;
import jakarta.persistence.EntityManager;


import java.util.Collections; // For returning empty list on error
import java.util.List;      // Import List


public class JpaMovieService {

    private JPAMovieDao movieDao;


    public JpaMovieService() {
        this.movieDao = new JPAMovieDao();
    }

    /**
     * Retrieves all movies from the database.
     *
     * @return A List of all Movie objects, or an empty list if none are found or an error occurs.
     */
    public List<Movie> getMovies() { // Changed return type to List<Movie>
        try {
            // Assuming filmDao has a method getMovies() that returns List<Movie>
            List<Movie> movies = movieDao.listFilm(); // Call the DAO method
            // Return the list directly (could be empty)
            return movies;

        } catch (Exception e) {
            // Return an empty list in case of error to avoid null pointers downstream
            return Collections.emptyList();
        }
        // Removed the Optional wrapping and printing logic
    }
}