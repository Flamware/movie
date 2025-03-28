package fr.utbm.controller;

import java.util.Scanner;

import fr.utbm.entity.Actor;
import fr.utbm.entity.Movie;

public class DefaultMovieController {
    public Movie registerMovieFromConsoleInput() {
        Integer id;
        String title;
        int copies;
        String movieType;
        Actor mainActor;
        int actorId;

    try (Scanner sc = new Scanner(System.in)) {
        System.out.println("Quel est le titre du film ?");
        title = sc.nextLine();

        System.out.println("Quel est l'identifiant du film ?");
        id = Integer.parseInt(sc.nextLine());

        System.out.println("Combien de copies sont disponibles ?");
        copies = Integer.parseInt(sc.nextLine());

        System.out.println("Quel est le type du film ?");
        movieType = sc.nextLine();

        System.out.println("Quel est le nom de l'acteur principal ?");
        String actorName = sc.nextLine();

        System.out.println("Quel est l'id de l'acteur principal ?");
        actorId = Integer.parseInt(sc.nextLine());

        mainActor = new Actor(actorId, actorName);
    }

    return new Movie(id, title, copies, movieType, mainActor);
    }
}