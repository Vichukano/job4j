package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.Transmission;
import ru.job4j.carprice.persistence.Dao;
import ru.job4j.carprice.persistence.TransmissionDaoImpl;

import java.util.List;

/**
 * Singleton class for service methods with Transmission objects.
 */
public class TransmissionService {
    private final Dao<Transmission> store = new TransmissionDaoImpl();
    private static final TransmissionService INSTANCE = new TransmissionService();
    private final Logger logger = LogManager.getLogger(TransmissionService.class);

    private TransmissionService() {

    }

    public static TransmissionService getInstance() {
        return INSTANCE;
    }

    public Transmission findById(long id) {
        try {
            return this.store.findById(id);
        } catch (Exception e) {
            logger.error("Failed to find transmission by id.", e);
            return null;
        }
    }

    public List<Transmission> findAll() {
        try {
            return this.store.findAll();
        } catch (Exception e) {
            logger.error("Failed to find all transmissions.", e);
            return null;
        }
    }
}
