package ru.job4j.carprice.persistence.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.job4j.carprice.configuration.Config;
import ru.job4j.carprice.configuration.WebbAppInit;
import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.model.Image;
import ru.job4j.carprice.persistence.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class, WebbAppInit.class})
@WebAppConfiguration
public class CarDaoImplTest {

    @Autowired
    private CarDao dao;

    @Autowired
    private CarBodyDao bodyDao;

    @Autowired
    private EngineDao engineDao;

    @Autowired
    private TransmissionDao trDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageDao imageDao;

    @Test
    public void whenFindAllThenReturnListOfCars() {
        assertThat(this.dao.findAll().size(), is(1));
    }

    @Test
    public void whenFindByIdThenReturnCar() {
        assertThat(this.dao.findById(1L).getName(), is("FORD"));
        assertThat(this.dao.findById(1L).getBody().getType(), is("sedan"));
        assertThat(this.dao.findById(1L).getEngine().getType(), is("petrol"));
        assertThat(this.dao.findById(1L).getTransmission().getType(), is("mechanical"));
    }

    @Test
    public void whenFindCarByNameThenReturnCar() {
        Car car = new Car();
        car.setName("FORD");
        assertThat(this.dao.findByName(car).get(0).getName(), is("FORD"));
    }

    @Test
    public void whenAddCarThenStoreIt() {
        Car car = new Car(
                "TOYOTA",
                100.00,
                "RED",
                this.bodyDao.findById(2L),
                this.engineDao.findById(2L),
                this.trDao.findById(2L),
                100500
                );
        Image image = new Image("empty");
        car.setImage(image);
        car.setUser(this.userDao.findById(1L));
        this.dao.add(car);
        assertThat(this.dao.findById(car.getId()).getName(), is("TOYOTA"));
        assertThat(this.dao.findById(car.getId()).getBody().getType(), is("hatchback"));
        assertThat(this.dao.findById(car.getId()).getEngine().getType(), is("diesel"));
        assertThat(this.dao.findById(car.getId()).getTransmission().getType(), is("automatic"));
        this.dao.delete(car.getId());
        this.imageDao.delete(image.getId());
    }
}
