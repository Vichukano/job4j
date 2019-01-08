package ru.job4j.todolist.dao;

import org.hibernate.Session;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.utils.HibernateSessionFactoryUtil;

import java.util.List;
import java.util.function.Function;

/**
 * Implementation of DAO-interface for CRUD-operations with item.
 */
public class ItemDaoImpl implements ItemDao {

    @Override
    public void add(Item item) throws DaoException {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DaoException("Failed to add item.", e);
        } finally {
            session.close();
        }

    }

    @Override
    public void delete(Item item) throws DaoException {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.delete(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DaoException("Failed to delete item.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Item item) throws DaoException {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.update(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new DaoException("Failed to update item.", e);
        } finally {
            session.close();
        }
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
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
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
}
