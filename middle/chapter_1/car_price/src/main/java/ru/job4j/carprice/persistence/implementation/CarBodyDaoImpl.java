package ru.job4j.carprice.persistence.implementation;

import ru.job4j.carprice.model.CarBody;
import ru.job4j.carprice.persistence.CarBodyDao;

public class CarBodyDaoImpl extends GenericDaoImpl<CarBody> implements CarBodyDao {

    public CarBodyDaoImpl() {
        super(CarBody.class);
    }
}
