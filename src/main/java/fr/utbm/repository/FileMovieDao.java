package fr.utbm.repository;

import fr.utbm.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FileMovieDao implements MovieDao {

    private static final Logger logger = LoggerFactory.getLogger(FileMovieDao.class);
    private final String filePath;

    public FileMovieDao(String filePath) {
        this.filePath = filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("Error creating file: {}", filePath, e);
            }
        }
    }

    @Override
    public void saveMovies(List<Movie> movies) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Movie movie : movies) {
                writer.write(movie.toString());
                writer.newLine();
            }
            logger.info("Movies successfully saved to: {}", filePath);
        } catch (IOException e) {
            logger.error("Error saving movies to file: {}", filePath, e);
        }
    }

    @Override
    public Movie getMovieById(int id) throws SQLException {
        throw new UnsupportedOperationException("getMovieById is not supported for FileMovieDao");
    }

    @Override
    public void addMovie(Movie movie) throws SQLException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(movie.toString());
            writer.newLine();
            logger.info("Movie successfully added to: {}", filePath);
        } catch (IOException e) {
            logger.error("Error adding movie to file: {}", filePath, e);
            throw new SQLException("Error adding movie to file", e);
        }
    }

    @Override
    public void updateMovie(Movie movie) throws SQLException {
        throw new UnsupportedOperationException("updateMovie is not supported for FileMovieDao");
    }

    @Override
    public void deleteMovie(int id) throws SQLException {
        throw new UnsupportedOperationException("deleteMovie is not supported for FileMovieDao");
    }

    @Override
    public List<Movie> getAllMovies() throws SQLException {
        throw new UnsupportedOperationException("getAllMovies is not supported for FileMovieDao");
    }
}