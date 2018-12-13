package ru.job4j.persistent;

import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.util.List;

/**
 * Interface for CRUD methods.
 */
public interface Store {

    boolean add(User user);

    boolean add(Role role);

    User update(int id, User user);

    boolean delete(int id);

    List<User> findAll();

    User findById(int id);

    User findByLogin(String name);

    List<Role> getRoles();

    Role findRoleByName(String name);

    int getCountryIdByName(String name);

    List<String> getCitiesByCountryId(int id);
}
