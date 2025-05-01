package fr.utbm;

import fr.utbm.core.util.HibernateUtil;
import fr.utbm.entity.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AppHibernate {

    private static final Logger logger = LoggerFactory.getLogger(AppHibernate.class);

    public static void main(String[] args) {

        Session session = null; // Declare session outside try for finally block access if needed
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // Begin a transaction
            session.beginTransaction();

            // --- Fetching all users using a query ---
            logger.info("Fetching all users...");
            // Create an HQL query to select all Users entities
            // Ensure your Users entity is correctly mapped (e.g., with @Entity)
            Query<Users> query = session.createQuery("FROM Users", Users.class);

            // Execute the query to get the list of users
            List<Users> users = query.list();

            // Process the fetched users
            if (users != null && !users.isEmpty()) {
                logger.info("Found {} users:", users.size());
                System.out.println("All Users:");
                // Iterate and print (or process) each user
                users.forEach(user -> System.out.println(user.toString())); // Make sure Users has a meaningful toString()
            } else {
                logger.info("No users found in the database.");
            }

            // Commit the transaction if everything was successful
            session.getTransaction().commit();
            logger.info("Transaction committed successfully.");

        } catch (Exception e) {
            logger.error("An error occurred during Hibernate operation:", e);
            // Rollback transaction in case of error if it's active
            if (session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
                try {
                    session.getTransaction().rollback();
                    logger.info("Transaction rolled back due to error.");
                } catch (Exception rbEx) {
                    logger.error("Could not rollback transaction", rbEx);
                }
            }
        } finally {
            // Close the session
            if (session != null && session.isOpen()) {
                session.close();
                logger.info("Session closed.");
            }
            // Shutdown the Hibernate SessionFactory
            // Note: Typically shutdown is called only once when the application stops,
            // not after every operation, unless this is a very simple standalone app.
            HibernateUtil.shutdown();
            logger.info("Hibernate SessionFactory shut down.");
        }
    }
}