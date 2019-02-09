package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.User;

public interface UserDao extends GenericDao<User> {

    User findByParam(User user);
}
