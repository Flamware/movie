package fr.utbm.core.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;

public class HibernateUtil {

    private static final EntityManagerFactory sessionFactory;

    static {
        try {
            // Specify the persistence unit name from your persistence.xml
            sessionFactory = Persistence.createEntityManagerFactory("movie");
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return sessionFactory.createEntityManager();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static EntityManagerFactory getSessionFactory() {
        return sessionFactory;
    }
}