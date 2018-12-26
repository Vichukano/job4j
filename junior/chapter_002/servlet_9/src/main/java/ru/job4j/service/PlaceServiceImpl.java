package ru.job4j.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.PlaceDao;
import ru.job4j.entity.Place;
import ru.job4j.persistence.PlaceRepository;

import java.util.List;

public class PlaceServiceImpl implements PlaceService {
    private final static PlaceService INSTANCE = new PlaceServiceImpl();
    private final PlaceDao placeStore = PlaceRepository.getPlaceStoreInstance();
    private final static Logger LOG = LogManager.getLogger(PlaceServiceImpl.class);

    private PlaceServiceImpl() {

    }

    public static PlaceService getPlaceServiceInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(Place place) {
        boolean result = this.placeStore.add(place);
        if (!result) {
            LOG.debug("Failed to add place: {}", place.toString());
        }
        return result;
    }

    @Override
    public boolean delete(Place place) {
        boolean result = this.placeStore.delete(place.getId());
        if (!result) {
            LOG.debug("Place with id {} is missing in database.", place.getId());
        }
        return result;
    }

    @Override
    public Place findById(Place place) {
        Place tmp = this.placeStore.findById(place.getId());
        if (tmp == null) {
            LOG.debug("Place with id {} is missing in database.", place.getId());
        }
        return tmp;
    }

    @Override
    public Place findByParam(Place place) {
        Place tmp = this.placeStore.findByParam(place);
        if (tmp == null) {
            LOG.debug(
                    "Place with row {} and col {} is missing in database",
                    place.getRow(),
                    place.getCol()
            );
        }
        return tmp;
    }

    @Override
    public List<Place> findAll() {
        return this.placeStore.findAll();
    }

    @Override
    public List<Place> findAllReserved() {
        return this.placeStore.findAllReserved();
    }

    @Override
    public boolean updatePlace(Place place) {
        return this.placeStore.updatePlace(place);
    }
}
