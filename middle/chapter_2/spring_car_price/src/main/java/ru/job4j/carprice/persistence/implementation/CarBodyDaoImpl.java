package ru.job4j.carprice.persistence.implementation;

import org.springframework.stereotype.Component;
import ru.job4j.carprice.model.CarBody;
import ru.job4j.carprice.persistence.CarBodyDao;

@Component
public class CarBodyDaoImpl extends GenericDaoImpl<CarBody> implements CarBodyDao {

    public CarBodyDaoImpl() {
        super(CarBody.class);
    }
}
