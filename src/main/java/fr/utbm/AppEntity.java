package fr.utbm;

import java.util.Calendar;

import fr.utbm.entity.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AppEntity {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("movie");
    private static EntityManager entityManager = emf.createEntityManager();
    public static void main(String[] args) {

        Test t = new Test();
        t.setDate(Calendar.getInstance().getTime());
        t.setLabel("test");
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
    }
}
