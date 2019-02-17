package ru.job4j.ioc.configurations;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ApplicationTest {

    @Test
    public void whenCreateCarWithGasolineEngineBeanThenInjectIt() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        Car car = (Car) ctx.getBean("gasoline");
        assertNotNull(car);
        assertNotNull(car.getEngine());
        assertThat(car.getEngine().getName(), is("GASOLINE"));
    }

    @Test
    public void whenCreateCarWithDieselEngineBeanThenInjectIt() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        Car car = (Car) ctx.getBean("diesel");
        assertNotNull(car);
        assertNotNull(car.getEngine());
        assertThat(car.getEngine().getName(), is("DIESEL"));
    }
}
