package ru.job4j.persistent;

import ru.job4j.model.Model;

import java.util.List;

/**
 * Base crud interface.
 * @param <T> entity class.
 */
public interface Store<T extends Model> {

    void add(T model);

    List<T> findAll();

    void delete(int id);
}
