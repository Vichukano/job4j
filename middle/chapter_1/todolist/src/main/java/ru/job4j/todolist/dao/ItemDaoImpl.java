package ru.job4j.todolist.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.utils.HibernateSessionFactoryUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implementation of DAO-interface for CRUD-operations with item.
 */
public class ItemDaoImpl implements ItemDao {
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil
            .getInstance()
            .getSessionFactoryInstance();

    @Override
    public void add(Item item) throws DaoException {
        this.tz(
                session -> session.save(item)
        );

    }

    @Override
    public void delete(Item item) throws DaoException {
        this.tz(
                session -> session.delete(item)
        );
    }

    @Override
    public void update(Item item) throws DaoException {
        this.tz(
                session -> session.update(item)
        );
    }

    @Override
    public Item findById(final int id) throws DaoException {
        return this.tx(
                session -> session.get(Item.class, id)
        );
    }

    @Override
    public List<Item> findAll() throws DaoException {
        return this.tx(
                session -> session.createQuery("from Item").list()
        );
    }

    @Override
    public List<Item> findAllDone() throws DaoException {
        return this.tx(
                session -> session.createQuery("from Item where done = true").list()
        );
    }

    private <T> T tx(final Function<Session, T> command) throws DaoException {
        final Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        try {
            return command.apply(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw new DaoException(e.getMessage());
        } finally {
            session.getTransaction().commit();
            session.close();
        }
    }

    private void tz(final Consumer<Session> consumer) throws DaoException {
        final Session session = this.sessionFactory.openSession();
        session.beginTransaction();
        try {
            consumer.accept(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw new DaoException(e.getMessage());
        } finally {
            session.getTransaction().commit();
            session.close();
        }
    }
}
