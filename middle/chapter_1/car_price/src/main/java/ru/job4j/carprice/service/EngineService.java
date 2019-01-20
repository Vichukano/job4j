package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.Engine;
import ru.job4j.carprice.persistence.Dao;
import ru.job4j.carprice.persistence.EngineDaoImpl;

import java.util.List;

public class EngineService {
    private final Dao<Engine> store = new EngineDaoImpl();
    private static final EngineService INSTANCE = new EngineService();
    private final Logger logger = LogManager.getLogger(EngineService.class);

    private EngineService() {

    }

    public static EngineService getInstance() {
        return INSTANCE;
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
