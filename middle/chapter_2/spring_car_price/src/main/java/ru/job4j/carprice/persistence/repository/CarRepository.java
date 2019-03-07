package ru.job4j.carprice.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carprice.model.Car;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findByName(String name);
}
