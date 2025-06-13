package fr.utbm.repository;

import fr.utbm.entity.Movie;
import fr.utbm.conf.Jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcMovieDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMovieDao.class);
    private final Jdbc jdbc;

    public JdbcMovieDao(Jdbc jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Retrieves a movie from the database by its ID.
     *
     * @param id The ID of the movie to retrieve.
     * @return The Movie object if found, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public Movie getMovieById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Movie movie = null;

        try {
            connection = jdbc.getConnection();
            String query = "SELECT id, title, director, release_year FROM movies WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setReleaseYear(resultSet.getInt("release_year"));
            }
        } catch (SQLException e) {
            logger.error("Error getting movie by ID", e);
            throw e;
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return movie;
    }

    /**
     * Adds a new movie to the database using a Statement.
     *
     * @param movie The Movie object to add.
     * @throws SQLException If a database access error occurs.
     */
    public void saveStatement(Movie movie) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = jdbc.getConnection();
            statement = connection.createStatement();
            String sql = "INSERT INTO movies (title, director, release_year) VALUES ('"
                    + movie.getTitle() + "', '"
                    + movie.getDirector() + "', "
                    + movie.getReleaseYear() + ")";
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                movie.setId(generatedKeys.getInt(1));
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error("Error adding movie using Statement", e);
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            closeResources(generatedKeys, statement, connection);
        }
    }

    /**
     * Adds a new movie to the database using a PreparedStatement.
     *
     * @param movie The Movie object to add.
     * @throws SQLException If a database access error occurs.
     */
    public void savePreparedStatement(Movie movie) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = jdbc.getConnection();
            String query = "INSERT INTO movies (title, director, release_year) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getDirector());
            preparedStatement.setInt(3, movie.getReleaseYear());
            preparedStatement.executeUpdate();

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                movie.setId(generatedKeys.getInt(1));
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error("Error adding movie using PreparedStatement", e);
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            closeResources(generatedKeys, preparedStatement, connection);
        }
    }
    /**
     * Adds a new movie to the database.
     *
     * @param movie The Movie object to add.
     * @throws SQLException If a database access error occurs.
     */
    public void addMovie(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, copies, movie_type, director, release_year) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbc.getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setInt(2, movie.getCopies()); // Make sure this is set correctly
            preparedStatement.setString(3, movie.getMovietype());
            preparedStatement.setString(4, movie.getDirector());
            preparedStatement.setInt(5, movie.getReleaseYear());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                // Get the generated ID (if id is SERIAL)
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    movie.setId(generatedKeys.getInt(1));
                }
            }
            logger.info("Movie added: {}", movie);

        } catch (SQLException e) {
            logger.error("Error adding movie", e);
            if (connection != null) {
                connection.rollback(); // Rollback on error
            }
            throw e;
        } finally {
            jdbc.closeConnection(connection);
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    /**
     * Updates an existing movie in the database.
     *
     * @param movie The Movie object to update.
     * @throws SQLException If a database access error occurs.
     */
    public void updateMovie(Movie movie) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = jdbc.getConnection();
            String query = "UPDATE movies SET title = ?, director = ?, release_year = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getDirector());
            preparedStatement.setInt(3, movie.getReleaseYear());
            preparedStatement.setInt(4, movie.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("Error updating movie", e);
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }

    /**
     * Deletes a movie from the database by its ID.
     *
     * @param  movieId ID of the movie to delete.
     * @throws SQLException If a database access error occurs.
     */
    public void deleteMovie(int movieId) throws SQLException {
        String sql = "DELETE FROM movies WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbc.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movieId);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Movie deleted with id: {}", movieId);
            } else {
                logger.warn("No movie found with id: {}", movieId);
            }
        } catch (SQLException e) {
            logger.error("Error deleting movie", e);
            // Remove connection.rollback();
            throw e;
        } finally {
            jdbc.closeConnection(connection);
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    /**
     * Retrieves all movies from the database.
     *
     * @return A list of all Movie objects in the database.
     * @throws SQLException If a database access error occurs.
     */
    public List<Movie> getAllMovies() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Movie> movies = new ArrayList<>();

        try {
            connection = jdbc.getConnection();
            String query = "SELECT id, title, director, release_year FROM movies";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDirector(resultSet.getString("director"));
                movie.setReleaseYear(resultSet.getInt("release_year"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            logger.error("Error getting all movies", e);
            throw e;
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return movies;
    }

    /**
     * Closes the database resources.
     *
     * @param resultSet         The ResultSet to close.
     * @param statement         The Statement or PreparedStatement to close.
     * @param connection        The Connection to close.
     */
    private void closeResources(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Error closing resources", e);
        }
    }
}