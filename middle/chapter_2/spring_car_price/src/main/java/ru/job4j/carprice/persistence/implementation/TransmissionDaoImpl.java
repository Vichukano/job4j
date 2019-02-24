package ru.job4j.carprice.persistence.implementation;

import org.springframework.stereotype.Component;
import ru.job4j.carprice.model.Transmission;
import ru.job4j.carprice.persistence.TransmissionDao;

@Component
public class TransmissionDaoImpl extends GenericDaoImpl<Transmission> implements TransmissionDao {

    public TransmissionDaoImpl() {
        super(Transmission.class);
    }
}
