package fr.utbm.repository;

import fr.utbm.entity.Movie;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HibernateFilmDao {
    private Session session;
    private static final Logger logger = LoggerFactory.getLogger(HibernateFilmDao.class);

    public HibernateFilmDao(Session session) {
        if (session == null) {
            // It's good practice to ensure the session is not null
            throw new IllegalArgumentException("Hibernate Session cannot be null.");
        }
        this.session = session;
    }


    public void saveMovie(Movie movie) {
        try {
            if (!session.isOpen()) {
                logger.error("Attempted to save movie with a closed session.");
                throw new IllegalStateException("Session is closed");
            }
            if (!session.getTransaction().isActive()) {
                logger.error("Attempted to save movie without an active transaction.");
                throw new IllegalStateException("Transaction is not active for save operation. Service layer should manage transactions.");
            }

            logger.debug("Persisting movie: {}", movie);
            session.persist(movie); // Use persist for new entities
            logger.debug("Movie persist requested.");

        } catch (Exception e) {
            logger.error("Error during movie persist operation: {}", e.getMessage(), e);
            throw new RuntimeException("Error saving movie", e);
        }
    }

    public Movie getMovieById(int id) {
        return session.get(Movie.class, id);
    }

    public void updateMovie(Movie movie) {
        // Removed session.beginTransaction();
        try {
            if (!session.isOpen()) {
                logger.error("Attempted to update movie with a closed session.");
                throw new IllegalStateException("Session is closed");
            }
            // Check if the transaction was started by the service layer
            if (!session.getTransaction().isActive()) {
                logger.error("Attempted to update movie without an active transaction.");
                throw new IllegalStateException("Transaction is not active for update operation. Service layer should manage transactions.");
            }

            logger.debug("Merging movie state: {}", movie);
            // Use merge instead of update - it's generally safer and handles detached entities correctly.
            // Hibernate will track changes and flush them when the service layer commits.
            session.merge(movie);
            logger.debug("Movie merge requested for ID: {}", movie.getId());
            // Removed session.getTransaction().commit();

        } catch (Exception e) {
            // Removed session.getTransaction().rollback();
            // Log error and re-throw for service layer to handle rollback
            logger.error("Error during movie merge operation for ID {}: {}", movie != null ? movie.getId() : "null", e.getMessage(), e);
            throw new RuntimeException("Error updating movie", e);
        }
    }

    public List<Movie> getMovies() {
        try {
            if (!session.isOpen()) {
                logger.error("Attempted to retrieve");
                throw new IllegalStateException("Session is closed");
            }
            if (!session.getTransaction().isActive()) {
                logger.error("Attempted to retrieve");
            }
            return session.createQuery("FROM Movie", Movie.class).getResultList();
        } catch (Exception e) {
            logger.error("Error during movie retrieval operation: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving movies", e);
        }
    }
}
