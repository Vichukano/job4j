package ru.job4j.carprice.persistence.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.persistence.CarDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Component
public class CarDaoImpl extends GenericDaoImpl<Car> implements CarDao {

    @Autowired
    private EntityManagerFactory factory;

    public CarDaoImpl() {
        super(Car.class);
    }

    @Override
    public List<Car> findByName(Car car) {
        List<Car> cars;
        EntityManager em = this.factory.createEntityManager();
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
