package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.persistence.CarDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    private final CarDao store;
    private final Map<Action.Type, Supplier<List<Car>>> dispatch = new HashMap<>();
    private final Logger logger = LogManager.getLogger(CarService.class);
    private EntityManagerFactory factory;

    @Autowired
    public CarService(CarDao store) {
        this.store = store;
    }

    @Autowired
    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void add(Car car) {
        try {
            this.store.add(car);
        } catch (Exception e) {
            logger.error("Failed to add car.", e);
        }
    }

    public void delete(Car car) {
        try {
            this.store.delete(car.getId());
        } catch (Exception e) {
            logger.error("Failed to delete car", e);
        }
    }

    public void update(Car car) {
        try {
            this.store.update(car);
        } catch (Exception e) {
            logger.error("Failed to update car.", e);
        }
    }

    public Car findById(Car car) {
        try {
            return this.store.findById(car.getId());
        } catch (Exception e) {
            logger.error("Failed to find by id.", e);
            return null;
        }
    }


    public List<Car> findByName(Car car) {
        try {
            return this.store.findByName(car);
        } catch (Exception e) {
            logger.error("Failed to find by name.", e);
            return null;
        }
    }

    public List<Car> findCarByPart(String query, String type) {
        EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Car> typedQuery = em.createNamedQuery(query, Car.class);
        typedQuery.setParameter("type", type);
        return typedQuery.getResultList();
    }

    public List<Car> findAll() {
        try {
            return this.store.findAll();
        } catch (Exception e) {
            logger.error("Failed to find all cars.", e);
            return null;
        }
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
