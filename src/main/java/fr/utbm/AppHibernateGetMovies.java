package fr.utbm;

import fr.utbm.controller.DefaultMovieController; // Import the controller
import fr.utbm.core.util.HibernateUtil;
// import fr.utbm.entity.Movie; // Keep if needed for other operations
import fr.utbm.services.MovieService;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class AppHibernateGetMovies {

    private static final Logger logger = LoggerFactory.getLogger(AppHibernate.class);

    public static void main(String[] args) {
        Session session = null;

        // Manage Scanner here using try-with-resources
        try (Scanner mainScanner = new Scanner(System.in)) {
            logger.info("Opening Hibernate session...");
            session = HibernateUtil.getSessionFactory().openSession();

            MovieService movieService = new MovieService(session);
            // Inject service into controller
            DefaultMovieController movieController = new DefaultMovieController(movieService);

            // Pass the single scanner instance
            movieController.getMovies();

            // You could call other controller methods here too, passing the same mainScanner

        } catch (Exception e) {
            logger.error("An unexpected error occurred in the main application flow:", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
                logger.info("Session closed.");
            } else {
                logger.info("Session was not opened or already closed.");
            }
            HibernateUtil.shutdown();
            logger.info("Hibernate SessionFactory shut down.");
        }
        // mainScanner is automatically closed here
    }
}