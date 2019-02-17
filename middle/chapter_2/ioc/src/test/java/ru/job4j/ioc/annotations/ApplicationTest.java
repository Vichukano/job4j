package ru.job4j.ioc.annotations;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.job4j.ioc.annotations.beans.Car;
import ru.job4j.ioc.annotations.config.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class ApplicationTest {

    @Test
    public void whenCreateCarWithDieselEngineBeanThenInjectIt() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        Car car = ctx.getBean(Car.class);
        assertNotNull(car.getEngine());
        assertThat(car.getEngine().getName(), is("DIESEL"));
    }
}
