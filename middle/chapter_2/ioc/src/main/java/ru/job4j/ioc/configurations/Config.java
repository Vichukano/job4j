package ru.job4j.ioc.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Engine getGasolineEngine() {
        GasolineEngine engine = new GasolineEngine();
        engine.setName("GASOLINE");
        return engine;
    }

    @Bean
    public Engine getDieselEngine() {
        DieselEngine engine = new DieselEngine();
        engine.setName("DIESEL");
        return engine;
    }

    @Bean("gasoline")
    public Car getGasolineCar() {
        Car car = new Car();
        car.setEngine(getGasolineEngine());
        return car;
    }

    @Bean("diesel")
    public Car getDieselCar() {
        Car car = new Car();
        car.setEngine(getDieselEngine());
        return car;
    }
}
