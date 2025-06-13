package fr.utbm.services;

import fr.utbm.entity.Actor; // Import Actor
import fr.utbm.entity.Movie;
// Use the interface for the DAO field type
import fr.utbm.repository.MovieDao; // Use the interface
import fr.utbm.repository.HibernateFilmDao; // Keep for instantiation if not using DI framework
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger; // Using SLF4j for logging
import org.slf4j.LoggerFactory; // Using SLF4j

import java.util.Collections; // For returning empty list on error
import java.util.List;      // Import List
import java.util.Optional;  // Use Optional for return type

public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final Session session;
    // Use the interface type for the DAO field
    private final HibernateFilmDao filmDao; // Changed type to MovieDao

    public MovieService(Session session) {
        this.session = session;
        // Instantiate the implementation, but assign to the interface type
        this.filmDao = new HibernateFilmDao(session);
    }


    /**
     * Retrieves a movie by its ID.
     *
     * @param id The ID of the movie to retrieve.
     * @return An Optional containing the Movie if found, otherwise an empty Optional.
     */
    public Optional<Movie> getMovieById(int id) {
        if (id <= 0) {
            logger.warn("Attempted to get movie with invalid ID: {}", id);
            return Optional.empty();
        }

        try {
            // Assuming filmDao.getMovieById now correctly returns Optional<Movie>
            Optional<Movie> movieOptional = Optional.ofNullable(filmDao.getMovieById(id)); // Removed Optional.ofNullable wrapper

            if (movieOptional.isPresent()) {
                logger.info("Movie found for ID {}: {}", id, movieOptional.get());
            } else {
                logger.info("No movie found for ID: {}", id);
            }
            return movieOptional;

        } catch (Exception e) {
            logger.error("Error retrieving movie with ID: {}", id, e);
            return Optional.empty();
        }
    }

    /**
     * Modifies the title of an existing movie.
     *
     * @param id    The ID of the movie to modify.
     * @param title The new title for the movie.
     * @throws IllegalArgumentException if the movie with the given ID is not found or title is invalid.
     * @throws RuntimeException         if a database error occurs during the update.
     */
    public void modifyTitle(Integer id, String title) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid movie ID provided for title modification.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("New title cannot be null or empty.");
        }

        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Fetch the movie using the DAO method that returns Optional
            Optional<Movie> movieOptional = Optional.ofNullable(filmDao.getMovieById(id)); // Use updated getMovieById

            // Check if the movie exists
            if (movieOptional.isPresent()) {
                Movie movieToUpdate = movieOptional.get();
                logger.info("Found movie ID {} to update title.", id);
                movieToUpdate.setTitle(title.trim()); // Set the new title

                // Use the DAO's update method (assuming MovieDao has updateMovie)
                filmDao.updateMovie(movieToUpdate);
                logger.info("Attempting to update title for movie: {}", movieToUpdate);

                tx.commit();
                logger.info("Successfully updated title for movie ID {}.", id);
            } else {
                // Movie not found, rollback and throw exception
                logger.warn("Movie with ID {} not found. Cannot modify title.", id);
                if (tx != null) tx.rollback(); // Rollback as the operation cannot proceed
                throw new IllegalArgumentException("Movie with ID " + id + " not found.");
            }
        } catch (Exception e) {
            // Rollback transaction on any error during the process
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                    logger.info("Transaction rolled back due to error during title modification for ID {}.", id);
                } catch (Exception rbEx) {
                    logger.error("Could not rollback transaction after title modification failure for ID {}.", id, rbEx);
                }
            }
            // Log the error and re-throw a runtime exception
            logger.error("Failed to modify title for movie ID {}.", id, e);
            throw new RuntimeException("Error while modifying movie title for ID " + id, e);
        }
    }

    /**
     * Retrieves all movies from the database.
     *
     * @return A List of all Movie objects, or an empty list if none are found or an error occurs.
     */
    public List<Movie> getMovies() { // Changed return type to List<Movie>
        try {
            // Assuming filmDao has a method getMovies() that returns List<Movie>
            List<Movie> movies = filmDao.getMovies(); // Call the DAO method
            logger.info("Retrieved {} movies.", movies.size());
            // Return the list directly (could be empty)
            return movies;

        } catch (Exception e) {
            logger.error("Error retrieving all movies.", e);
            // Return an empty list in case of error to avoid null pointers downstream
            return Collections.emptyList();
        }
        // Removed the Optional wrapping and printing logic
    }
}