package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.Transmission;
import ru.job4j.carprice.persistence.TransmissionDao;

import java.util.List;

/**
 * Class for service methods with Transmission objects.
 * Singleton by default.
 */
@Service
public class TransmissionService {
    private final TransmissionDao store;
    private final Logger logger = LogManager.getLogger(TransmissionService.class);

    @Autowired
    public TransmissionService(TransmissionDao store) {
        this.store = store;
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
