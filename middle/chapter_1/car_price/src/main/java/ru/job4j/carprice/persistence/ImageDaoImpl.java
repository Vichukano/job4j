package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.Image;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ImageDaoImpl implements Dao<Image> {
    private final EntityManagerFactory factory = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();

    @Override
    public void add(Image image) {
        this.tz(
                entityManager -> entityManager.persist(image)
        );
    }

    @Override
    public void delete(Image image) {
        this.tz(
                entityManager -> entityManager.remove(image)
        );
    }

    @Override
    public void update(Image image) {
        this.tz(
                entityManager -> entityManager.merge(image)
        );
    }

    @Override
    public Image findCarById(long id) {
        return this.tx(
                entityManager -> entityManager.find(Image.class, id)
        );
    }

    @Override
    public List<Image> findAll() {
        return this.tx(
                entityManager -> entityManager.createQuery("from Image").getResultList()
        );
    }

    @Override
    public List<Image> findByName(Image image) {
        return this.tx(
                entityManager -> entityManager
                        .createQuery("from Image where url=" + image.getUrl())
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
