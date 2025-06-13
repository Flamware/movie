package fr.utbm;

import fr.utbm.conf.Jdbc;
import fr.utbm.entity.Movie;
import fr.utbm.repository.JdbcMovieDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class AppJdbc {

   private static final Logger logger = LoggerFactory.getLogger(AppJdbc.class);

   public static void main(String[] args) {
      // Database credentials
      String url = "jdbc:postgresql://localhost:5432/movie";
      String user = "admin";
      String password = "admin";

      Jdbc jdbc = new Jdbc(url, user, password);
      JdbcMovieDao movieDao = new JdbcMovieDao(jdbc);

      try {
         // Example usage: Get all movies
         List<Movie> movies = movieDao.getAllMovies();
         for (Movie movie : movies) {
            System.out.println(movie);
         }

         // Example usage: Get a movie by ID
         Movie movie = movieDao.getMovieById(1);
         if (movie != null) {
            System.out.println("Movie with ID 1: " + movie);
         } else {
            System.out.println("Movie with ID 1 not found.");
         }


      } catch (SQLException e) {
         logger.error("Database error", e);
      }
   }
}