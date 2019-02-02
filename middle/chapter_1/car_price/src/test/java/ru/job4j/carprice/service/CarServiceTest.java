package ru.job4j.carprice.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.carprice.model.*;
import ru.job4j.carprice.persistence.CarBodyDaoImpl;
import ru.job4j.carprice.persistence.Dao;
import ru.job4j.carprice.persistence.EngineDaoImpl;
import ru.job4j.carprice.persistence.TransmissionDaoImpl;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CarServiceTest {
    private final CarService service = CarService.getInstance();
    private final Dao<CarBody> dao = new CarBodyDaoImpl();
    private final Dao<Engine> engineDao = new EngineDaoImpl();
    private final Dao<Transmission> trDao = new TransmissionDaoImpl();

    @Before
    public void setUp() {
        CarBody sedan = new CarBody("sedan");
        CarBody hatchback = new CarBody("hatchback");
        Engine petrol = new Engine("petrol");
        Engine diesel = new Engine("diesel");
        Transmission mechanical = new Transmission("mechanical");
        Transmission robot = new Transmission("robot");
        Image one = new Image("one");
        Image two = new Image("two");
        Image three = new Image("empty");
        Date createDate = new Date(System.currentTimeMillis());
        Car first = new Car();
        Car second = new Car();
        Car third = new Car();
        Car fourth = new Car();
        Car fifth = new Car();
        this.dao.add(sedan);
        this.dao.add(hatchback);
        this.engineDao.add(petrol);
        this.engineDao.add(diesel);
        this.trDao.add(mechanical);
        this.trDao.add(robot);
        first.setBody(sedan);
        first.setEngine(petrol);
        first.setTransmission(mechanical);
        first.setImage(one);
        second.setBody(sedan);
        second.setEngine(petrol);
        second.setTransmission(mechanical);
        second.setImage(two);
        third.setBody(sedan);
        third.setEngine(diesel);
        third.setTransmission(robot);
        third.setImage(three);
        fourth.setBody(hatchback);
        fourth.setEngine(diesel);
        fourth.setTransmission(robot);
        fifth.setBody(hatchback);
        fifth.setEngine(diesel);
        fifth.setTransmission(robot);
        first.setCreateDate(createDate);
        second.setCreateDate(createDate);
        third.setCreateDate(createDate);
        first.setSold(true);
        second.setSold(true);
        this.service.add(first);
        this.service.add(second);
        this.service.add(third);
        this.service.add(fourth);
        this.service.add(fifth);
    }

    @After
    public void reset() {
        EntityManager em = EntityManagerFactoryUtil
                .getInstance()
                .getEntityManagerFactory()
                .createEntityManager();
        em.getTransaction().begin();
        Query carQuery = em.createQuery("delete from Car ");
        Query bodyQuery = em.createQuery("delete from CarBody ");
        Query engineQuery = em.createQuery("delete from Engine ");
        Query trQuery = em.createQuery("delete from Transmission ");
        Query imageQuery = em.createQuery("delete from Image ");
        carQuery.executeUpdate();
        bodyQuery.executeUpdate();
        engineQuery.executeUpdate();
        trQuery.executeUpdate();
        imageQuery.executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void whenFindAllCarsThenReturnList() {
        List<Car> cars = this.service.findAll();
        assertThat(cars.size(), is(5));
    }

    @Test
    public void whenFindAllCarsThenReturnListOfCarsWithCarBodies() {
        List<Car> cars = this.service.findAll();
        assertThat(cars.get(0).getBody().getType(), is("sedan"));
        assertThat(cars.get(1).getBody().getType(), is("sedan"));
        assertThat(cars.get(2).getBody().getType(), is("sedan"));
        assertThat(cars.get(3).getBody().getType(), is("hatchback"));
        assertThat(cars.get(4).getBody().getType(), is("hatchback"));
    }

    @Test
    public void whenFindCarBySedanBodyThenReturnList() {
        List<Car> sedans = this.service.findCarByPart("findCarByBody", "sedan");
        assertThat(sedans.size(), is(3));
        assertThat(sedans.get(0).getBody().getType(), is("sedan"));
        assertThat(sedans.get(1).getBody().getType(), is("sedan"));
        assertThat(sedans.get(2).getBody().getType(), is("sedan"));
    }

    @Test
    public void whenFindCarByPetrolEngineThenReturnList() {
        List<Car> petrol = this.service.findCarByPart("findCarByEngine", "petrol");
        assertThat(petrol.size(), is(2));
        assertThat(petrol.get(0).getEngine().getType(), is("petrol"));
        assertThat(petrol.get(1).getEngine().getType(), is("petrol"));
    }

    @Test
    public void whenFindCarByDieselEngineThenReturnList() {
        List<Car> diesel = this.service.findCarByPart("findCarByEngine", "diesel");
        assertThat(diesel.size(), is(3));
        assertThat(diesel.get(0).getEngine().getType(), is("diesel"));
        assertThat(diesel.get(1).getEngine().getType(), is("diesel"));
        assertThat(diesel.get(2).getEngine().getType(), is("diesel"));
    }

    @Test
    public void whenFindCarByMechanicalTrThenReturnList() {
        List<Car> mechanical = this.service.findCarByPart("findCarByTransmission", "mechanical");
        assertThat(mechanical.size(), is(2));
        assertThat(mechanical.get(0).getTransmission().getType(), is("mechanical"));
        assertThat(mechanical.get(1).getTransmission().getType(), is("mechanical"));
    }

    @Test
    public void whenFindCarByRobotTrThenReturnList() {
        List<Car> robot = this.service.findCarByPart("findCarByTransmission", "robot");
        assertThat(robot.size(), is(3));
        assertThat(robot.get(0).getTransmission().getType(), is("robot"));
        assertThat(robot.get(1).getTransmission().getType(), is("robot"));
        assertThat(robot.get(2).getTransmission().getType(), is("robot"));
    }

    @Test
    public void whenFindCarWithImageThenReturnList() {
        List<Car> cars = this.service.findCarWithImage();
        assertThat(cars.size(), is(2));
        assertThat(cars.get(0).getImage().getUrl(), is("one"));
        assertThat(cars.get(1).getImage().getUrl(), is("two"));
    }

    @Test
    public void whenFindCarForLastDayThenReturnList() {
        List<Car> cars = this.service.findCarForLastDay();
        assertThat(cars.size(), is(3));
    }

    @Test
    public void whenUseDispatchGetAllThenReturnListOfCars() {
        Action.Type all = Action.Type.valueOf("all".toUpperCase());
        List<Car> cars = this.service.init().action(all);
        assertThat(cars.size(), is(5));
    }

    @Test
    public void whenUseDispatchWithImageThenReturnListOfCars() {
        Action.Type all = Action.Type.valueOf("image".toUpperCase());
        List<Car> cars = this.service.init().action(all);
        assertThat(cars.size(), is(2));
        assertThat(cars.get(0).getImage().getUrl(), is("one"));
        assertThat(cars.get(1).getImage().getUrl(), is("two"));
    }

    @Test
    public void whenUseDispatchForLastDayThenReturnListOfCars() {
        Action.Type all = Action.Type.valueOf("last".toUpperCase());
        List<Car> cars = this.service.init().action(all);
        assertThat(cars.size(), is(3));
    }

    @Test
    public void whenUseDispatchFindRelevantThenReturnList() {
        Action.Type relevant = Action.Type.valueOf("relevant".toUpperCase());
        List<Car> cars = this.service.init().action(relevant);
        assertThat(cars.size(), is(3));
    }

}
