package ru.job4j.todolist.dao;

import org.hibernate.Session;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.utils.HibernateSessionFactoryUtil;

import java.util.List;

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
    public Item findById(int id) throws DaoException {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        try {
            Item item = session.get(Item.class, id);
            return item;
        } catch (Exception e) {
            throw new DaoException("Failed to find item by id.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Item> findAll() throws DaoException {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        try {
            List<Item> items = (List<Item>) session.createQuery("from Item").list();
            return items;
        } catch (Exception e) {
            throw new DaoException("Failed to find all items.", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Item> findAllDone() throws DaoException {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        try {
            List<Item> items = (List<Item>) session.createQuery("from Item where done = true").list();
            return items;
        } catch (Exception e) {
            throw new DaoException("Failed to find done items", e);
        } finally {
            session.close();
        }
    }
}
