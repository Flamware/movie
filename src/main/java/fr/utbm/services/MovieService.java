package fr.utbm.services;

import fr.utbm.entity.Movie;
import fr.utbm.repository.FileMovieDao; // Fixed import for FileMovieDao
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieService {

    private static final Logger LOGGER = Logger.getLogger(MovieService.class.getName());

    public void registerMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }

        try {
            FileMovieDao dao = new FileMovieDao("movies.txt"); // Path to the file where movies are stored
            dao.addMovie(movie); // Assuming this method exists in FileMovieDao
            LOGGER.info("Movie registered successfully: " + movie);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to register movie: " + movie, e);
            throw new RuntimeException("Error while registering the movie", e);
        }
    }
}
