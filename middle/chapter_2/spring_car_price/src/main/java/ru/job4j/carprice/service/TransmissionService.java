package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.Transmission;
import ru.job4j.carprice.persistence.repository.TransmissionRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Class for service methods with Transmission objects.
 * Singleton by default.
 */
@Service
public class TransmissionService {
    private final TransmissionRepository repository;
    private final Logger logger = LogManager.getLogger(TransmissionService.class);

    @Autowired
    public TransmissionService(TransmissionRepository repository) {
        this.repository = repository;
    }

    public Transmission findById(long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public List<Transmission> findAll() {
        return (List<Transmission>) this.repository.findAll();
    }
}
