package ru.job4j.carprice.persistence.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.persistence.GenericDao;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    private EntityManagerFactory factory = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();
    private final Class<T> entityClass;
    private final Logger logger = LogManager.getLogger(GenericDaoImpl.class);

    public GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void add(T entity) {
        this.acceptConsumer(
                entityManager -> entityManager.persist(entity)
        );
    }

    @Override
    public void delete(long id) {
        this.acceptConsumer(
                entityManager -> entityManager
                        .createQuery("DELETE FROM " + entityClass.getName() + " e WHERE e.id =:id")
                        .setParameter("id", id)
                        .executeUpdate()
        );
    }

    @Override
    public void update(T entity) {
        this.acceptConsumer(
                entityManager -> entityManager.merge(entity)
        );
    }

    @Override
    public T findById(long id) {
        return this.applyFunction(
                entityManager -> entityManager.find(entityClass, id)
        );
    }

    @Override
    public List<T> findAll() {
        return this.applyFunction(
                entityManager -> entityManager
                        .createQuery("FROM " + entityClass.getName())
                        .getResultList()
        );
    }

    private void acceptConsumer(final Consumer<EntityManager> consumer) {
        final EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        try {
            consumer.accept(em);
        } catch (final Exception e) {
            em.getTransaction().rollback();
            logger.debug("Failed to execute operation! {}", e.getMessage());
            throw e;
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    private <T> T applyFunction(final Function<EntityManager, T> command) {
        final EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        try {
            return command.apply(em);
        } catch (final Exception e) {
            em.getTransaction().rollback();
            logger.debug("Failed to execute operation! {}", e.getMessage());
            throw e;
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    public EntityManagerFactory getFactory() {
        return this.factory;
    }

}
