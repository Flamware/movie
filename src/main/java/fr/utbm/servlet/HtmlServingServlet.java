package fr.utbm.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "HtmlServingServlet", urlPatterns = "/serve-html")
public class HtmlServingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: Entering doGet for /serve-html"); // Check if this prints
        try {
            // ... your logic ...
            System.out.println("DEBUG: Attempting to forward to /my-template.html"); // Check if this prints
            RequestDispatcher dispatcher = request.getRequestDispatcher("/my-template.html");
            dispatcher.forward(request, response);
            System.out.println("DEBUG: Forward completed."); // Check if this prints
        } catch (Exception e) {
            System.err.println("ERROR: An exception occurred in doGet: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace
            throw new ServletException(e); // Re-throw to ensure container error handling
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}