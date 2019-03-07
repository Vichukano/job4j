package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.Engine;
import ru.job4j.carprice.persistence.repository.EngineRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Class for service methods with Engine objects.
 * Singleton by default.
 */
@Service
public class EngineService {
    private final EngineRepository repository;
    private final Logger logger = LogManager.getLogger(EngineService.class);

    @Autowired
    public EngineService(EngineRepository repository) {
        this.repository = repository;
    }

    public Engine findById(long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public List<Engine> findAll() {
        return (List<Engine>) this.repository.findAll();
    }
}
