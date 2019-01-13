package ru.job4j.carprice.persistence;


import java.util.List;

public interface Dao<T> {

    void add(T model);

    void delete(T model);

    void update(T model);

    T findCarById(long id);

    List<T> findAll();

    List<T> findByName(T model);
}
