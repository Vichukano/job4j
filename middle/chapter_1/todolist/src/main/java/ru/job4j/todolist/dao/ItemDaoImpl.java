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
    public void add(Item item) {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public void delete(Item item) {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.delete(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Item item) {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.update(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Item findById(int id) {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        try {
            Item item = session.get(Item.class, id);
            return item;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Item> findAll() {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        try {
            List<Item> items = (List<Item>) session.createQuery("from Item").list();
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Item> findAllDone() {
        final Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        try {
            List<Item> items = (List<Item>) session.createQuery("from Item where done = true").list();
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }
}
