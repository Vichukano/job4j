package ru.job4j.carprice.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Util class for build and get EntityManagerFactory.
 */
@Configuration
public class EntityManagerFactoryUtil {
    /**
     * Method for building EntityManagerFactory.
     * Get parameters of persistence unit from persistence.xml.
     * @return EntityManagerFactory
     */
    private EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("car_price");
    }

    @Bean
    public EntityManagerFactory getEntityManagerFactory() {
        EntityManagerFactory factory = this.buildEntityManagerFactory();
        return factory;
    }
}
