package fr.utbm.servlet; // Ensure this matches your actual package

import fr.utbm.controller.DefaultMovieController;
import fr.utbm.controller.JpaMovieController;
import fr.utbm.entity.Movie;
import fr.utbm.repository.JPAMovieDao;
import fr.utbm.services.JpaMovieService;
import fr.utbm.services.MovieService;
import fr.utbm.tools.HibernateUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "VideostoreBackofficeServlet", urlPatterns = {"/backoffice"}) // Adjust the URL pattern as needed
public class VideostoreBackofficeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initialize the MovieService
        JpaMovieService movieService = new JpaMovieService();
        // Fetch all movies
        List<Movie> movies = movieService.getMovies();
        // Set the movies as a request attribute
        request.setAttribute("movies", movies);
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        request.setAttribute("movies", movies);
        // Forward to the JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/backoffice.jsp");
        dispatcher.forward(request, response);
    }
}