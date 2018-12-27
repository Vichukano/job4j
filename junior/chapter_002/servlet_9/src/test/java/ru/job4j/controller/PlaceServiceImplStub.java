package ru.job4j.controller;

import ru.job4j.entity.Place;
import ru.job4j.service.PlaceService;

import java.util.Arrays;
import java.util.List;

public class PlaceServiceImplStub implements PlaceService {

    @Override
    public boolean add(Place place) {
        return false;
    }

    @Override
    public boolean delete(Place place) {
        return false;
    }

    @Override
    public Place findById(Place place) {
        return null;
    }

    @Override
    public Place findByParam(Place place) {
        return null;
    }

    @Override
    public List<Place> findAll() {
        return null;
    }

    @Override
    public List<Place> findAllReserved() {
        return Arrays.asList(
                new Place(1,1),
                new Place(2, 2),
                new Place(3,3)
        );
    }

    @Override
    public boolean updatePlace(Place place) {
        return false;
    }
}
