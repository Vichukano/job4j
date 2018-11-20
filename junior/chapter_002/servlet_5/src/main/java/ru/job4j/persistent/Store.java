package ru.job4j.persistent;

import ru.job4j.model.User;

import java.util.List;

/**
 * Interface for CRUD methods.
 */
public interface Store {

    boolean add(User user);

    User update(int id, User user);

    boolean delete(int id);

    List<User> findAll();

    User findById(int id);

    List<User> getUsers();

}
