package fr.utbm.repository;

import fr.utbm.entity.Movie;

import java.sql.SQLException;
import java.util.List;

public interface MovieDao {

    void saveMovies(List<Movie> movies) throws SQLException;

    Movie getMovieById(int id) throws SQLException;

    void addMovie(Movie movie) throws SQLException;

    void updateMovie(Movie movie) throws SQLException;

    void deleteMovie(int id) throws SQLException;

    List<Movie> getAllMovies() throws SQLException;
}