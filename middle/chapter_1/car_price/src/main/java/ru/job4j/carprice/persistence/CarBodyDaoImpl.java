package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.CarBody;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CarBodyDaoImpl implements Dao<CarBody> {
    private final EntityManagerFactory factory = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();

    @Override
    public void add(CarBody body) {
        this.tz(
                entityManager -> entityManager.persist(body)
        );
    }

    @Override
    public void delete(CarBody body) {
        this.tz(
                entityManager -> entityManager.remove(body)
        );
    }

    @Override
    public void update(CarBody body) {
        this.tz(
                entityManager -> entityManager.merge(body)
        );
    }

    @Override
    public CarBody findCarById(long id) {
        return this.tx(
                entityManager -> entityManager.find(CarBody.class, id)
        );
    }

    @Override
    public List<CarBody> findAll() {
        return this.tx(
                entityManager -> entityManager
                        .createQuery("from CarBody")
                        .getResultList()
        );
    }

    @Override
    public List<CarBody> findByName(CarBody body) {
        return this.tx(
                entityManager -> entityManager
                        .createQuery("from CarBody where type =" + body.getType())
                        .getResultList()
        );
    }

    private <T> T tx(final Function<EntityManager, T> command) {
        final EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        try {
            return command.apply(em);
        } catch (final Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    private void tz(final Consumer<EntityManager> consumer) {
        final EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        try {
            consumer.accept(em);
        } catch (final Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }
}
