package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.CarBody;
import ru.job4j.carprice.persistence.repository.CarBodyRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Class for service methods with CarBody objects.
 * Singleton by default.
 */
@Service
public class CarBodyService {
    private final CarBodyRepository repository;
    private final Logger logger = LogManager.getLogger(CarBodyService.class);

    @Autowired
    public CarBodyService(CarBodyRepository repository) {
        this.repository = repository;
    }

    /**
     * Method for finding CarBody object by id.
     *
     * @param id - id of object in database.
     * @return CarBody object.
     * @throws EntityNotFoundException if CarBody with this
     *                                 id not found in database.
     */
    public CarBody findById(long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    /**
     * Method for finding all CarBody objects from database.
     *
     * @return List of objects or null if not persist.
     */
    public List<CarBody> findAll() {
        return (List<CarBody>) this.repository.findAll();
    }
}
