package fr.utbm.core.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registery
                registry = new StandardServiceRegistryBuilder().configure().build();

                // Create Metadata
                MetadataSources sources = new MetadataSources(registry);

                // Create SessionFactory
                sessionFactory = sources.buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
            }
        return sessionFactory;
    }

    public static void shutdown(){
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
