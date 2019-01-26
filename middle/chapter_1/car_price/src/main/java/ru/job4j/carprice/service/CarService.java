package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.persistence.CarDaoImpl;
import ru.job4j.carprice.persistence.Dao;

import java.util.List;

/**
 * Singleton class for service methods with Car object.
 */
public class CarService {
    private final Dao<Car> store = new CarDaoImpl();
    private static final CarService INSTANCE = new CarService();
    private final Logger logger = LogManager.getLogger(CarService.class);

    private CarService() {

    }

    public static CarService getInstance() {
        return INSTANCE;
    }

    public void add(Car car) {
        try {
            this.store.add(car);
        } catch (Exception e) {
            logger.error("Failed to add car.", e);
        }
    }

    public void delete(Car car) {
        try {
            this.store.delete(car);
        } catch (Exception e) {
            logger.error("Failed to delete car", e);
        }
    }

    public void update(Car car) {
        try {
            this.store.update(car);
        } catch (Exception e) {
            logger.error("Failed to update car.", e);
        }
    }

    public Car findById(Car car) {
        try {
            return this.store.findById(car.getId());
        } catch (Exception e) {
            logger.error("Failed to find by id.", e);
            return null;
        }
    }

    public List<Car> findAll() {
        try {
            return this.store.findAll();
        } catch (Exception e) {
            logger.error("Failed to find all cars.", e);
            return null;
        }
    }

    public List<Car> findByName(Car car) {
        try {
            return this.store.findByName(car);
        } catch (Exception e) {
            logger.error("Failed to find by name.", e);
            return null;
        }
    }
}
