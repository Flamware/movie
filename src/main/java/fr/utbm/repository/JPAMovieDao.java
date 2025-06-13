package fr.utbm.repository;

import fr.utbm.entity.Movie;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class JPAMovieDao {
    // MovieDao will receive an EntityManager
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("video");
    EntityManager entityManager = null;

    public void save(Movie f) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(f);
        entityManager.getTransaction().commit();
    }

    public ArrayList<Movie> listFilm() {
        ArrayList<Movie> films = new ArrayList<Movie>();
        entityManager = entityManagerFactory.createEntityManager();
        Query q = entityManager.createQuery("from Movie ");
        films = (ArrayList<Movie>) q.getResultList();
        return films;
    }

    public Movie getMovieById(Long movieId) {
        entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Movie.class, movieId);
    }

    public void update(Movie f) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(f);
        entityManager.getTransaction().commit();
    }
}
