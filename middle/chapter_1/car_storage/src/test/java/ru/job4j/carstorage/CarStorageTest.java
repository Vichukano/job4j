package ru.job4j.carstorage;

import org.junit.Test;
import ru.job4j.carstorage.model.Car;
import ru.job4j.carstorage.model.CarBody;
import ru.job4j.carstorage.model.Engine;
import ru.job4j.carstorage.model.Transmission;
import ru.job4j.carstorage.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
        em.getTransaction().commit();
        em.close();
        assertThat(cars.size(), is(1));
        assertThat(cars.get(0).getName(), is("Toyota"));
        assertThat(cars.get(0).getBody().getName(), is("black"));
        assertThat(cars.get(0).getEngine().getName(), is("1.6"));
        assertThat(cars.get(0).getTransmission().getName(), is("mechanical"));
    }

    @Test
    public void whenEditCatThenChangeParameters() {
        Car car = new Car();
        car.setName("Ford");
        CarBody body = new CarBody("blue");
        Engine engine = new Engine("1.8");
        Transmission transmission = new Transmission("automatic");
        EntityManager em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(body);
        em.persist(engine);
        em.persist(transmission);
        car.setEngine(engine);
        car.setBody(body);
        car.setTransmission(transmission);
        em.persist(car);
        assertThat(car.getName(), is("Ford"));
        assertThat(car.getBody().getName(), is("blue"));
        assertThat(car.getEngine().getName(), is("1.8"));
        assertThat(car.getTransmission().getName(), is("automatic"));
        em.getTransaction().commit();
        em.close();
        em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Car ford = em.createQuery("from Car where name ='Ford'", Car.class).getSingleResult();
        ford.setName("Lexus");
        em.getTransaction().commit();
        em.close();
        em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Car lexus = em.createQuery("from Car where name ='Lexus'", Car.class).getSingleResult();
        assertThat(lexus.getName(), is("Lexus"));
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Exception throws when trying to get 'removed' Car object from db.
     */
    @Test(expected = NoResultException.class)
    public void whenDeleteCarThenDeleteItFromDatabase() {
        Car car = new Car();
        car.setName("UAZ");
        CarBody body = new CarBody("green");
        Engine engine = new Engine("2.0");
        Transmission transmission = new Transmission("4MT");
        EntityManager em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(body);
        em.persist(engine);
        em.persist(transmission);
        car.setEngine(engine);
        car.setBody(body);
        car.setTransmission(transmission);
        em.persist(car);
        em.getTransaction().commit();
        em.close();
        em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Car uaz = em.createQuery("from Car where name ='UAZ'", Car.class).getSingleResult();
        assertThat(uaz.getName(), is("UAZ"));
        em.remove(uaz);
        em.getTransaction().commit();
        em.close();
        em = this.entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Car removed = em.createQuery("from Car where name ='UAZ'", Car.class).getSingleResult();
        em.getTransaction().commit();
        em.close();
    }
}
