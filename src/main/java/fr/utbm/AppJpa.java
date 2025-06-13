package fr.utbm;

import fr.utbm.entity.Actor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import fr.utbm.entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.Query;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class AppJpa {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        SpringApplication.run(AppJpa.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            EntityManager entityManager = null;
            EntityTransaction transaction = null;
            try {
                System.out.println("Opening Hibernate session...");
                entityManager = entityManagerFactory.createEntityManager();
                transaction = entityManager.getTransaction();
                transaction.begin();

                entityManager.clear(); // Clear the persistence context

                // Print all movies. Actors are loaded lazily.
                Query query = entityManager.createQuery("SELECT m FROM Movie m", Movie.class);
                List<Movie> movies = query.getResultList();

                System.out.println("Movies (lazy loading actors):");
                for (Movie movie : movies) {
                    System.out.println("ID: " + movie.getId() + ", Title: " + movie.getTitle() + ", Director: " + movie.getDirector() + ", Release Year: " + movie.getReleaseYear() + ", Movie Type: " + movie.getMovietype() + ", Copies: " + movie.getCopies());
                    if (movie.getActors() != null) {
                        System.out.println("  Actors:");
                        for(Actor actor : movie.getActors()){
                            System.out.println("    ID: " + actor.getId() + ", First Name: " + actor.getFirstname() + ", Last Name: " + actor.getLastname());
                        }
                    }
                }

                transaction.commit();
                System.out.println("Movie list printed successfully!");

            } catch (Exception e) {
                System.err.println("An unexpected error occurred in the main application flow:");
                e.printStackTrace();
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            } finally {
                System.out.println("Closing Hibernate session...");
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        };
    }
}