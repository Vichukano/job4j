package ru.job4j.todolist.dao;

import ru.job4j.todolist.model.Item;

import java.util.List;

/**
 * Interface for CRUD-operations with item.
 */
public interface ItemDao {

    void add(Item item) throws DaoException;

    void delete(Item item) throws DaoException;

    void update(Item item) throws DaoException;

    Item findById(int id) throws DaoException;

    List<Item> findAll() throws DaoException;

    List<Item> findAllDone() throws DaoException;
}
