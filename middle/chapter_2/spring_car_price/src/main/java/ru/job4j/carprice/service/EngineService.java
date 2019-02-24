package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.Engine;
import ru.job4j.carprice.persistence.EngineDao;

import java.util.List;

/**
 * Singleton class for service methods with Engine objects.
 */
@Service
public class EngineService {
    private final EngineDao store;
    private final Logger logger = LogManager.getLogger(EngineService.class);

    @Autowired
    public EngineService(EngineDao store) {
        this.store = store;
    }

    public Engine findById(long id) {
        try {
            return this.store.findById(id);
        } catch (Exception e) {
            logger.error("Failed to find engine by id.", e);
            return null;
        }
    }

    public List<Engine> findAll() {
        try {
            return this.store.findAll();
        } catch (Exception e) {
            logger.error("Failed to find all engines.", e);
            return null;
        }
    }
}
