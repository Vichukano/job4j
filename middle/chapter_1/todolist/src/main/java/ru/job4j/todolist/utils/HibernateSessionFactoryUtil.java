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
    private static final HibernateSessionFactoryUtil INSTANCE = new HibernateSessionFactoryUtil();
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    /**
     * Default constructor.
     */
    private HibernateSessionFactoryUtil() {
    }

    public static HibernateSessionFactoryUtil getInstance() {
        return INSTANCE;
    }

    public SessionFactory getSessionFactoryInstance() {
        return SESSION_FACTORY;
    }

    /**
     * Method for configure and getting sessionFactory instance.
     * Thread-safe.
     * Lazy-loading.
     *
     * @return sessionFactory instance.
     */
    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Item.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }
}
