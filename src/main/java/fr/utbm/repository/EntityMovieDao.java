package fr.utbm.repository;

import fr.utbm.core.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class EntityMovieDao {
    private EntityManager entityManager;
    private static EntityMovieDao instance;
    public EntityMovieDao() {
        entityManager = HibernateUtil.getEntityManager();
    }
    public static EntityMovieDao getInstance() {
        if (instance == null) {
            instance = new EntityMovieDao();
        }
        return instance;
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
    public void beginTransaction() {
        if (entityManager != null && !entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }
    public void commitTransaction() {
        if (entityManager != null && entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }
    public void rollbackTransaction() {
        if (entityManager != null && entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }
    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
    public void shutdown() {
        HibernateUtil.shutdown();
    }
    public void clear() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.clear();
        }
    }
    public void flush() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.flush();
        }
    }
    public boolean isOpen() {
        return entityManager != null && entityManager.isOpen();
    }
}
