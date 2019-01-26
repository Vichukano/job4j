package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.CarBody;
import ru.job4j.carprice.persistence.CarBodyDaoImpl;
import ru.job4j.carprice.persistence.Dao;

import java.util.List;

/**
 * Singleton class for service methods with CarBody objects.
 */
public class CarBodyService {
    private final Dao<CarBody> store = new CarBodyDaoImpl();
    private static final CarBodyService INSTANCE = new CarBodyService();
    private final Logger logger = LogManager.getLogger(CarBodyService.class);

    private CarBodyService() {

    }

    public static CarBodyService getInstance() {
        return INSTANCE;
    }

    /**
     * Method for finding CarBody object by id.
     * @param id - id of object in database.
     * @return CarBody object or null if not persist.
     */
    public CarBody findById(long id) {
        try {
            return this.store.findById(id);
        } catch (Exception e) {
            logger.error("Failed to find body by id.", e);
            return null;
        }
    }

    /**
     * Method for finding all CarBody objects from database.
     * @return List of objects or null if not persist.
     */
    public List<CarBody> findAll() {
        try {
            return this.store.findAll();
        } catch (Exception e) {
            logger.error("Failed to find all bodies.", e);
            return null;
        }
    }
}
