package ru.job4j.carprice.persistence.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.carprice.persistence.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Abstract class with default crud methods with entities.
 *
 * @param <T> entity object.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    private EntityManagerFactory factory;
    private final Class<T> entityClass;
    private final Logger logger = LogManager.getLogger(GenericDaoImpl.class);

    public GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Autowired
    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
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

    /**
     * Wrapper method for void crud methods with entities.
     *
     * @param consumer consumer with EntityManager object.
     */
    private void acceptConsumer(final Consumer<EntityManager> consumer) {
        final EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        try {
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (final Exception e) {
            em.getTransaction().rollback();
            logger.debug("Failed to execute operation! {}", e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Wrapper method for crud methods with entities
     * that return value.
     *
     * @param command function that apply EntityManager object
     *                and Entity object.
     * @param <T>     object that return method.
     * @return object - result of method.
     */
    private <T> T applyFunction(final Function<EntityManager, T> command) {
        final EntityManager em = this.factory.createEntityManager();
        em.getTransaction().begin();
        try {
            T result = command.apply(em);
            em.getTransaction().commit();
            return result;
        } catch (final Exception e) {
            em.getTransaction().rollback();
            logger.debug("Failed to execute operation! {}", e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }
}
