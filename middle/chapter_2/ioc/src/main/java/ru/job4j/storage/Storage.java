package ru.job4j.storage;

import java.util.List;

public interface Storage {

    void add(User user);

    void delete(User user);

    User findById(int id);

    List<User> findAll();
}
