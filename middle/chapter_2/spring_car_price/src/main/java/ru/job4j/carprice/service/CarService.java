package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.persistence.repository.CarRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Class for service methods with Car object.
 * Singleton by default.
 */
@Service
public class CarService {
    private final CarRepository repository;
    private final Map<Action.Type, Supplier<List<Car>>> dispatch = new HashMap<>();
    private final Logger logger = LogManager.getLogger(CarService.class);
    private EntityManagerFactory factory;

    @Autowired
    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void add(Car car) {
        this.repository.save(car);
        logger.debug("Added car: {}", car.toString());
    }

    public void delete(Car car) {
        this.repository.delete(car);
        logger.debug("Car with id={} deleted.", car.getId());
    }

    public void update(Car car) {
        this.repository.save(car);
        logger.debug("Car with id={} updated.", car.getId());
    }

    public Car findById(Car car) {
        return this.repository
                .findById(car.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(car.getId())));
    }


    public List<Car> findByName(Car car) {
        return this.repository.findByName(car.getName());
    }

    public List<Car> findCarByPart(String query, String type) {
        EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Car> typedQuery = em.createNamedQuery(query, Car.class);
        typedQuery.setParameter("type", type);
        return typedQuery.getResultList();
    }

    public List<Car> findAll() {
        return (List<Car>) this.repository.findAll();
    }

    public List<Car> findCarWithImage() {
        EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Car> typedQuery = em.createNamedQuery("findCarWithImage", Car.class);
        typedQuery.setParameter("url", "empty");
        return typedQuery.getResultList();
    }

    public List<Car> findCarForLastDay() {
        EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Car> typedQuery = em.createNamedQuery("findCarForLastDay", Car.class);
        return typedQuery.getResultList();
    }

    public List<Car> findRelevantCars() {
        EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Car> typedQuery = em.createNamedQuery("findRelevant", Car.class);
        return typedQuery.getResultList();
    }

    public Supplier<List<Car>> getAll() {
        return this::findAll;
    }

    public Supplier<List<Car>> withImage() {
        return this::findCarWithImage;
    }

    public Supplier<List<Car>> forLastDay() {
        return this::findCarForLastDay;
    }

    public Supplier<List<Car>> findRelevant() {
        return this::findRelevantCars;
    }

    public CarService init() {
        this.load(Action.Type.ALL, getAll());
        this.load(Action.Type.IMAGE, withImage());
        this.load(Action.Type.LAST, forLastDay());
        this.load(Action.Type.RELEVANT, findRelevant());
        return this;
    }

    private void load(Action.Type type, Supplier<List<Car>> handle) {
        this.dispatch.put(type, handle);
    }

    public List<Car> action(Action.Type type) {
        return this.dispatch.get(type).get();
    }
}
