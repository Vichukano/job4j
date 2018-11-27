package ru.job4j.persistent;

import ru.job4j.model.Model;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.util.List;

/**
 * Interface for CRUD methods.
 */
public interface Store<T extends Model> {

    boolean add(T model);

    T update(int id, T model);

    boolean delete(int id);

    List<T> findAll();

    T findById(int id);

    T findByName(String name);

    List<T> getUsers();
}
