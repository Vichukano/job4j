package ru.job4j.logic;

import ru.job4j.model.User;

import java.util.List;

public interface Validate {
    boolean add(User user);

    boolean update(int id, User user);

    boolean delete(int id);

    List<User> findAll();

    User findById(int id);

    List<User> getUsers();
}
