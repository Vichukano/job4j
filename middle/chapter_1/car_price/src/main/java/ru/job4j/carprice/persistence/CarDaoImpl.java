package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

public class CarDaoImpl implements CarDao {
    private final EntityManagerFactory factory = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();

    @Override
    public void add(Car car) {
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Car car) {
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(car);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Car car) {
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(car);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
        }
    }

    @Override
    public Car findCarById(Car car) {
        Car found = null;
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            found = em.find(Car.class, car.getId());
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
        }
        return found;
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars;
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            cars = em.createQuery("from Car").getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
        }
        return cars;
    }

    @Override
    public List<Car> findByName(Car car) {
        List<Car> cars;
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            cars = em.createQuery("from Car where name =" + car.getName()).getResultList();
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
