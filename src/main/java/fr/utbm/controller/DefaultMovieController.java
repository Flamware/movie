package fr.utbm.controller;

import java.util.InputMismatchException; // Import for specific exception
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fr.utbm.entity.Actor;
import fr.utbm.entity.Movie;
// Removed direct DAO import, controller should use service
// import fr.utbm.repository.HibernateFilmDao;
import fr.utbm.services.MovieService; // Import MovieService
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMovieController {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMovieController.class);

    // Service should be injected - make it final
    private final MovieService movieService;

    // --- Constructor for Dependency Injection ---
    public DefaultMovieController(MovieService movieService) {
        if (movieService == null) {
            // Ensure service is provided
            throw new IllegalArgumentException("MovieService cannot be null.");
        }
        this.movieService = movieService;
    }

    /**
     * Prompts the user via the console to enter details for a new movie
     * (excluding the movie ID, which is assumed to be database-generated).
     * Performs basic input validation.
     *
     * @param scanner The Scanner instance to use for input.
     * @return A Movie object populated with the user's input (ID will be null),
     * or null if an unrecoverable error occurs during input.
     */
    // Pass Scanner as argument
    public Movie registerMovieFromConsoleInput(Scanner scanner) {
        // ID is no longer asked from the user.

        String title = null;
        Integer copies = null; // Use Integer for parsing loop
        String movieType = null;
        String director = null;
        Integer releaseYear = null;
        Integer actorId = null; // Use Integer for parsing loop

        // Removed internal Scanner creation and try-with-resources
        try {
            // --- Get Title (non-blank) ---
            while (title == null || title.trim().isEmpty()) {
                System.out.println("Quel est le titre du film ?");
                // Use the passed scanner
                title = scanner.nextLine().trim();
                if (title.isEmpty()) {
                    logger.warn("Le titre du film ne peut pas être vide.");
                }
            }

            // --- Get Director (non-blank) ---
            while (director == null || director.trim().isEmpty()) {
                System.out.println("Quel est le nom du réalisateur ?");
                // Use the passed scanner
                director = scanner.nextLine().trim();
                if (director.isEmpty()) {
                    logger.warn("Le nom du réalisateur ne peut pas être vide.");
                }
            }

            // --- Get Release Year (valid integer) ---
            while (releaseYear == null) {
                System.out.println("Quelle est l'année de sortie ?");
                try {
                    // Use the passed scanner
                    releaseYear = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    logger.warn("Format de l'année invalide. Veuillez entrer un nombre entier.");
                }
            }


            // --- Get Copies (non-negative integer) ---
            while (copies == null) {
                System.out.println("Combien de copies sont disponibles ?");
                try {
                    // Use the passed scanner
                    int copiesInput = Integer.parseInt(scanner.nextLine());
                    if (copiesInput >= 0) {
                        copies = copiesInput;
                    } else {
                        logger.warn("Le nombre de copies ne peut pas être négatif.");
                    }
                } catch (NumberFormatException e) {
                    logger.warn("Format du nombre de copies invalide. Veuillez entrer un nombre entier.");
                }
            }

            // --- Get Movie Type (non-blank) ---
            while (movieType == null || movieType.trim().isEmpty()) {
                System.out.println("Quel est le type du film (ex: Action, Comédie) ?");
                // Use the passed scanner
                movieType = scanner.nextLine().trim();
                if (movieType.isEmpty()) {
                    logger.warn("Le type du film ne peut pas être vide.");
                }
            }

            // --- Get Actor Info ---
            // Get Actor ID (valid positive integer, assuming it references an existing actor)
            while (actorId == null) {
                System.out.println("Quel est l'identifiant (ID) de l'acteur principal existant ?");
                try {
                    // Use the passed scanner
                    int parsedId = Integer.parseInt(scanner.nextLine());
                    if (parsedId > 0) { // Basic validation assuming IDs are positive
                        actorId = parsedId;
                    } else {
                        logger.warn("L'ID de l'acteur doit être un nombre positif.");
                    }
                } catch (NumberFormatException e) {
                    logger.warn("Format de l'ID de l'acteur invalide. Veuillez entrer un nombre entier.");
                }
            }

            // --- Create Movie Object ---
            Actor mainActorProxy = new Actor();
            mainActorProxy.setId(actorId); // Assuming Actor has setId()

            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setDirector(director); // Assuming setter exists
            movie.setReleaseYear(releaseYear); // Assuming setter exists
            movie.setCopies(copies);
            movie.setMovieType(movieType);
            movie.setMainActor(mainActorProxy); // Set the proxy actor

            logger.info("Movie details collected: {}", movie); // Log collected details
            return movie;

        } catch (InputMismatchException e) {
            logger.error("Erreur de type d'entrée inattendue.", e);
            return null;
        } catch (NoSuchElementException e) {
            logger.error("Input stream closed unexpectedly.", e);
            return null;
        } catch (Exception e) {
            logger.error("Une erreur inattendue est survenue lors de la saisie des informations du film.", e);
            return null;
        }
        // Scanner is NOT closed here - managed by the caller
    }

    /**
     * Prompts the user via the console to enter a movie ID.
     * Performs basic input validation.
     *
     * @param scanner The Scanner instance to use for input.
     * @return The Integer ID entered by the user, or null if an error occurs.
     */
    // Pass Scanner as argument
    public Integer getMovieIdFromConsoleInput(Scanner scanner) {
        Integer id = null;

        // Removed internal Scanner creation and try-with-resources
        try {
            System.out.println("Quel est l'identifiant du film ?");
            while (id == null) {
                try {
                    // Use the passed scanner
                    id = Integer.parseInt(scanner.nextLine());
                    if (id <= 0) { // Optional: Add validation for positive ID
                        logger.warn("L'identifiant du film doit être un nombre positif.");
                        id = null; // Reset to loop again if invalid
                    }
                } catch (NumberFormatException e) {
                    logger.warn("Format de l'identifiant invalide. Veuillez entrer un nombre entier.");
                }
            }
            logger.info("Movie ID collected: {}", id);
            return id;

        } catch (InputMismatchException e) {
            logger.error("Erreur de type d'entrée inattendue lors de la saisie de l'ID.", e);
            return null;
        } catch (NoSuchElementException e) {
            logger.error("Input stream closed unexpectedly while getting ID.", e);
            return null;
        } catch (Exception e) {
            logger.error("Une erreur inattendue est survenue lors de la saisie de l'identifiant du film.", e);
            return null;
        }
        // Scanner is NOT closed here - managed by the caller
    }

    /**
     * Prompts the user for a movie ID and a new title, then attempts
     * to update the movie title via the MovieService.
     *
     * @param scanner The Scanner instance to use for input.
     */
    // Pass Scanner as argument
    public void modifyMovieTitle(Scanner scanner) {
        // Get the movie ID first, passing the scanner
        Integer id = getMovieIdFromConsoleInput(scanner);

        if (id != null) {
            String title = null;
            try {
                // Loop until a non-empty title is entered
                while (title == null || title.trim().isEmpty()) {
                    System.out.println("Quel est le nouveau titre du film ?");
                    // Use the passed scanner - DO NOT create a new one here
                    title = scanner.nextLine().trim();
                    if (title.isEmpty()) {
                        logger.warn("Le titre du film ne peut pas être vide.");
                    }
                }

                // Call the service method within a try-catch block
                try {
                    movieService.modifyTitle(id, title); // Use injected service
                    logger.info("Successfully requested title modification for movie ID {} to '{}'", id, title);
                    System.out.println("Modification du titre demandée avec succès."); // User feedback

                } catch (Exception serviceEx) {
                    logger.error("Failed to modify title for movie ID {}: {}", id, serviceEx.getMessage(), serviceEx);
                    System.err.println("Erreur lors de la modification du titre: " + serviceEx.getMessage()); // User feedback
                }

            } catch (NoSuchElementException e) {
                // This catch block in modifyMovieTitle handles the specific error you saw
                logger.error("Input stream closed unexpectedly while getting title.", e);
                System.err.println("Erreur: Impossible de lire l'entrée utilisateur car le flux est fermé.");
            } catch (Exception e) {
                logger.error("Une erreur inattendue est survenue lors de la saisie du nouveau titre.", e);
            }
        } else {
            logger.warn("Aborting title modification because movie ID could not be obtained.");
        }
        // Scanner is NOT closed here - managed by the caller
    }

    /**
     * Retrieves all movies from the service and displays them on the console.
     */
    public void getMovies() {
        logger.info("Attempting to retrieve all movies...");
        try {
            // Call the service method to get the list of movies
            List<Movie> movies = movieService.getMovies(); // Assuming this returns List<Movie>

            // Check if the list is empty or null (though service should ideally return empty list, not null)
            if (movies == null || movies.isEmpty()) {
                logger.info("No movies found in the database.");
                System.out.println("Aucun film trouvé dans la base de données.");
            } else {
                logger.info("Successfully retrieved {} movies.", movies.size());
                System.out.println("\n--- Liste des Films ---");
                // Iterate through the list and print each movie
                for (Movie movie : movies) {
                    // Assumes Movie entity has a meaningful toString() method
                    System.out.println(movie);
                }
                System.out.println("----------------------\n");
            }
        } catch (Exception e) {
            // Handle potential exceptions from the service layer (e.g., database connection issues)
            logger.error("Failed to retrieve movies due to an error: {}", e.getMessage(), e);
            System.err.println("Erreur lors de la récupération de la liste des films: " + e.getMessage());
        }
    }
}