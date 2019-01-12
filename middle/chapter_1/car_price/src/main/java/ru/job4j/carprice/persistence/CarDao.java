package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.Car;

import java.util.List;

public interface CarDao {

    void add(Car car);

    void delete(Car car);

    void update(Car car);

    Car findCarById(Car car);

    List<Car> findAll();

    List<Car> findByName(Car car);
}
