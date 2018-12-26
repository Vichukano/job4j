package ru.job4j.service;

import ru.job4j.entity.Place;

import java.util.List;

public interface PlaceService {

    boolean add(Place place);

    boolean delete(Place place);

    Place findById(Place place);

    Place findByParam(Place place);

    List<Place> findAll();

    List<Place> findAllReserved();

    boolean updatePlace(Place place);
}
