package ru.job4j.carprice.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Util class for build and get EntityManagerFactory.
 */
public class EntityManagerFactoryUtil {
    private static final EntityManagerFactoryUtil INSTANCE = new EntityManagerFactoryUtil();
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = buildEntityManagerFactory();

    private EntityManagerFactoryUtil() {

    }

    public static EntityManagerFactoryUtil getInstance() {
        return INSTANCE;
    }

    /**
     * Method for building EntityManagerFactory.
     * Get parameters of persistence unit from persistence.xml.
     * @return EntityManagerFactory
     */
    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("car_price");
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }
}
