package fr.utbm.controller;

import java.util.Scanner;

import fr.utbm.entity.Actor;
import fr.utbm.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMovieController {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMovieController.class);

    public Movie registerMovieFromConsoleInput() {
        Integer id = null;
        String title = null;
        int copies = 0;
        String movieType = null;
        Actor mainActor = null;
        int actorId = 0;
        String actorName = null;

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Quel est le titre du film ?");
            title = sc.nextLine();

            System.out.println("Quel est l'identifiant du film ?");
            try {
                id = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                logger.error("Invalid movie ID format", e);
                return null;
            }

            System.out.println("Combien de copies sont disponibles ?");
            try {
                copies = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                logger.error("Invalid copies format", e);
                return null;
            }

            System.out.println("Quel est le type du film ?");
            movieType = sc.nextLine();

            System.out.println("Quel est le nom de l'acteur principal ?");
            actorName = sc.nextLine();

            System.out.println("Quel est l'id de l'acteur principal ?");
            try {
                actorId = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                logger.error("Invalid actor ID format", e);
                return null;
            }

            mainActor = new Actor(actorId, actorName);
        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
            return null;
        }

        return new Movie(id, title, copies, movieType, mainActor);
    }
}