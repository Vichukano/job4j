package ru.job4j.carprice.persistence.implementation;

import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.persistence.CarDao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CarDaoImpl extends GenericDaoImpl<Car> implements CarDao {

    public CarDaoImpl() {
        super(Car.class);
    }

    @Override
    public List<Car> findByName(Car car) {
        List<Car> cars;
        EntityManager em = getFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("FROM " + Car.class.getName() + " c WHERE c.name =:model");
            query.setParameter("model", car.getName());
            cars = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
        }
        return cars;
    }
}
