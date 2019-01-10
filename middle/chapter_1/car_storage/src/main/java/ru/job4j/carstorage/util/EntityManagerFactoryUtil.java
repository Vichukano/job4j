package ru.job4j.carstorage.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryUtil {
    private static final EntityManagerFactoryUtil INSTANCE = new EntityManagerFactoryUtil();
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = buildEntityManagerFactory();

    private EntityManagerFactoryUtil() {

    }

    public static EntityManagerFactoryUtil getInstance() {
        return INSTANCE;
    }

    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("car_storage");
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }
}
