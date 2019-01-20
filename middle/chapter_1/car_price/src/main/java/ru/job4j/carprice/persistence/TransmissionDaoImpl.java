package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.Transmission;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransmissionDaoImpl implements Dao<Transmission> {
    private final EntityManagerFactory factory = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();

    @Override
    public void add(Transmission ts) {
        this.tz(
                entityManager -> entityManager.persist(ts)
        );
    }

    @Override
    public void delete(Transmission ts) {
        this.tz(
                entityManager -> entityManager.remove(ts)
        );
    }

    @Override
    public void update(Transmission ts) {
        this.tz(
                entityManager -> entityManager.merge(ts)
        );
    }

    @Override
    public Transmission findById(long id) {
        return this.tx(
                entityManager -> entityManager.find(Transmission.class, id)
        );
    }

    @Override
    public List<Transmission> findAll() {
        return this.tx(
                entityManager -> entityManager.createQuery("from Transmission").getResultList()
        );
    }

    @Override
    public List<Transmission> findByName(Transmission ts) {
        return this.tx(
                entityManager -> entityManager
                        .createQuery("from Transmission where type=" + ts.getType())
                        .getResultList()
        );
    }

    @Override
    public Transmission findByParam(Transmission model) {
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
