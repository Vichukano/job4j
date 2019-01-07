package ru.job4j.todolist.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.job4j.todolist.model.Item;

/**
 * Utility class for getting hibernate sessionFactory.
 * Singleton.
 */
public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    /**
     * Default constructor.
     */
    private HibernateSessionFactoryUtil() {

    }

    /**
     * Method for configure and getting sessionFactory instance.
     * Thread-safe.
     * Lazy-loading.
     *
     * @return sessionFactory instance.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (SessionFactory.class) {
                if (sessionFactory == null) {
                    try {
                        Configuration configuration = new Configuration().configure();
                        configuration.addAnnotatedClass(Item.class);
                        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties());
                        sessionFactory = configuration.buildSessionFactory(builder.build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sessionFactory;
    }
}
