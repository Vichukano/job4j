package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.Car;

import java.util.List;

public interface CarDao extends GenericDao<Car> {

    List<Car> findByName(Car car);
}
