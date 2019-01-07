package ru.job4j.todolist.dao;

import ru.job4j.todolist.model.Item;

import java.util.List;

/**
 * Interface for CRUD-operations with item.
 */
public interface ItemDao {

    void add(Item item);

    void delete(Item item);

    void update(Item item);

    Item findById(int id);

    List<Item> findAll();

    List<Item> findAllDone();
}
