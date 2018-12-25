package ru.job4j.dao;

import ru.job4j.entity.Place;

import java.util.List;

public interface PlaceDao {

    boolean add(Place place);

    boolean delete(int id);

    Place findById(int id);

    List<Place> findAll();

    Place findByParam(Place place);

    List<Place> findAllReserved();

    boolean updatePlace(Place place);
}
