package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.Engine;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class EngineDaoImpl implements Dao<Engine> {
    private final EntityManagerFactory factory = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();

    @Override
    public void add(Engine engine) {
        this.tz(
                entityManager -> entityManager.persist(engine)
        );
    }

    @Override
    public void delete(Engine engine) {
        this.tz(
                entityManager -> entityManager.remove(engine)
        );
    }

    @Override
    public void update(Engine engine) {
        this.tz(
                entityManager -> entityManager.merge(engine)
        );
    }

    @Override
    public Engine findById(long id) {
        return this.tx(
                entityManager -> entityManager.find(Engine.class, id)
        );
    }

    @Override
    public List<Engine> findAll() {
        return this.tx(
                entityManager -> entityManager.createQuery("from Engine").getResultList()
        );
    }

    @Override
    public List<Engine> findByName(Engine engine) {
        return this.tx(
                entityManager -> entityManager
                        .createQuery("from Engine where type=" + engine.getType())
                        .getResultList()
        );
    }

    @Override
    public Engine findByParam(Engine model) {
        return null;
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
