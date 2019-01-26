package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.Car;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.util.List;

/**
 * Implementation of Dao interface for Car objects.
 */
public class CarDaoImpl implements Dao<Car> {
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
            Query query = em.createQuery("delete from Car where id =:id");
            query.setParameter("id", car.getId());
            query.executeUpdate();
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
    public Car findById(long id) {
        Car found = null;
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            found = em.find(Car.class, id);
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
            Query query = em.createQuery("from Car where name =:name");
            query.setParameter("name", car.getName());
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

    @Override
    public Car findByParam(Car model) {
        return null;
    }
}
