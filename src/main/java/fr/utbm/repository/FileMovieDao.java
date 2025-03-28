package fr.utbm.repository;

import java.io.FileWriter;
import fr.utbm.entity.Movie;
import java.io.IOException;

public class FileMovieDao {

    public void addMovieToDB(Movie movie) {
        FileWriter writer;
        try {
            writer = new FileWriter("C:\\temps\\films.txt", true);
            writer.write(movie.toString()); // Assuming Movie has a proper toString() implementation
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    