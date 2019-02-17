package ru.job4j.ioc.xml;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


public class ApplicationTest {

    @Test
    public void whenCreateCarWithGasolineEngineBeanThenInjectIt() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        Car car = (Car) ctx.getBean("gasolineCar");
        assertNotNull(car);
        assertNotNull(car.getEngine());
        assertThat(car.getEngine().getName(), is("GASOLINE"));
    }

    @Test
    public void whenCreateCarWithDieselEngineBeanThenInjectIt() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        Car car = (Car) ctx.getBean("dieselCar");
        assertNotNull(car);
        assertNotNull(car.getEngine());
        assertThat(car.getEngine().getName(), is("DIESEL"));
    }
}
