package ru.job4j.logic;

import ru.job4j.model.User;

import java.util.List;
import java.util.function.Function;

public interface Validate {
    Function<User, Boolean> add();

    Function<User, Boolean> update();

    Function<User, Boolean> delete();

    List<User> findAll();

    User findById(int id);

    List<User> getUsers();

    Validate init();

    User findByLogin(String login);

    boolean isCredential(String login, String password);

    boolean isExist(String login);

    Boolean action(Action.Type type, User user);
}
