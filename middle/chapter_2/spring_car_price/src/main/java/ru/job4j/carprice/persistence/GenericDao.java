package ru.job4j.carprice.persistence;

import java.util.List;

public interface GenericDao<T> {

    void add(T entity);

    void delete(long id);

    void update(T entity);

    T findById(long id);

    List<T> findAll();
}
