package ru.job4j.carprice.persistence.implementation;

import ru.job4j.carprice.model.Transmission;
import ru.job4j.carprice.persistence.TransmissionDao;

public class TransmissionDaoImpl extends GenericDaoImpl<Transmission> implements TransmissionDao {

    public TransmissionDaoImpl() {
        super(Transmission.class);
    }
}
