package ru.job4j.carprice.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarDaoImplTest {
    private final Dao<Car> dao = new CarDaoImpl();


    @Before
    public void setUp() {
        Car toyota = new Car();
        toyota.setName("toyota");
        Car ford = new Car();
        ford.setName("ford");
        this.dao.add(toyota);
        this.dao.add(ford);
    }


    @After
    public void reset() {
        EntityManager em = EntityManagerFactoryUtil
                .getInstance()
                .getEntityManagerFactory()
                .createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("delete from Car");
        query.executeUpdate();
        em.getTransaction().commit();
    }


    @Test
    public void whenAddCarThenStoreIt() {
        Car mazda = new Car();
        mazda.setName("mazda");
        this.dao.add(mazda);
        List<Car> cars = this.dao.findAll();
        assertThat(cars.size(), is(3));
    }

    @Test
    public void whenUpdateCarThenUpdateItInStore() {
        List<Car> cars = this.dao.findAll();
        long id = cars.get(0).getId();
        Car car = this.dao.findById(id);
        car.setName("UPDATED");
        this.dao.update(car);
        Car updated = this.dao.findById(id);
        assertThat(updated.getName(), is("UPDATED"));
    }

    @Test
    public void whenFindByNameThenReturnListOfCars() {
        Car car = new Car();
        car.setName("toyota");
        List<Car> cars = this.dao.findByName(car);
        assertThat(cars.size(), is(1));
        assertThat(cars.get(0).getName(), is("toyota"));
    }

    @Test
    public void whenDeleteCarThenDeleteItFromStore() {
        List<Car> cars = this.dao.findAll();
        long id = cars.get(0).getId();
        Car car = new Car();
        car.setId(id);
        this.dao.delete(car);
        List<Car> carList = this.dao.findAll();
        assertThat(carList.size(), is(1));
    }
}
