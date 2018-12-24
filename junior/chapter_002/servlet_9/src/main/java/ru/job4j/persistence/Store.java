package ru.job4j.persistence;

import java.util.List;

public interface Store<T> {

    boolean add(T model);

    boolean delete(int id);

    T findById(int id);

    List<T> findAll();

    default List<T> findAllReserved() {
        return null;
    }

    default boolean reservePlace(T place) {
        return false;
    }
}
