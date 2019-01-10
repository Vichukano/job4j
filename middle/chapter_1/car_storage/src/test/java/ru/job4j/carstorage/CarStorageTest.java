package ru.job4j.carstorage;

import org.junit.Test;
import ru.job4j.carstorage.model.Car;
import ru.job4j.carstorage.model.CarBody;
import ru.job4j.carstorage.model.Engine;
import ru.job4j.carstorage.model.Transmission;
import ru.job4j.carstorage.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CarStorageTest {
    private final EntityManagerFactory entityManagerFactory
            = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();

    @Test
    public void whenAddCarWithComponentsThenStoreIt() {
        EntityManager em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        CarBody body = new CarBody("black");
        Engine engine = new Engine("1.6");
        Transmission transmission = new Transmission("mechanical");
        em.persist(body);
        em.persist(engine);
        em.persist(transmission);
        Car toyota = new Car();
        toyota.setName("Toyota");
        toyota.setBody(body);
        toyota.setEngine(engine);
        toyota.setTransmission(transmission);
        em.persist(toyota);
        em.getTransaction().commit();
        em.close();
        em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Car> cars = em.createQuery("from Car", Car.class).getResultList();
        assertThat(cars.size(), is(1));
        assertThat(cars.get(0).getName(), is("Toyota"));
        assertThat(cars.get(0).getBody().getName(), is("black"));
        assertThat(cars.get(0).getEngine().getName(), is("1.6"));
        assertThat(cars.get(0).getTransmission().getName(), is("mechanical"));
        em.getTransaction().commit();
        em.close();
    }
}
