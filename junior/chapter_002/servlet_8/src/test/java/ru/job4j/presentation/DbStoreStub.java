package ru.job4j.presentation;

import ru.job4j.model.City;
import ru.job4j.model.Country;
import ru.job4j.model.Role;
import ru.job4j.model.User;
import ru.job4j.persistent.Store;

import java.util.*;

/**
 * Stub class of DbStore for testing servlets.
 */
public class DbStoreStub implements Store {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Role> roles = new HashMap<>();
    private int userId;
    private int roleId;

    @Override
    public boolean add(User model) {
        boolean result = false;
        model.setId(this.userId++);
        this.users.put(model.getId(), model);
        if (this.users.size() > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean add(Role role) {
        boolean result = false;
        role.setRoleId(this.roleId++);
        this.roles.put(role.getRoleId(), role);
        if (this.users.size() > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public User update(int id, User model) {
        return this.users.replace(model.getId(), model);
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        if (this.users.remove(id) != null) {
            result = true;
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User findByLogin(String name) {
        return null;
    }

    @Override
    public List<Role> getRoles() {
        return new ArrayList<>(roles.values());
    }

    @Override
    public Role findRoleByName(String name) {
        Role role = new Role(name);
        if (this.roles.containsValue(role)) {
            return role;
        } else {
            return null;
        }
    }

    @Override
    public List<City> getCitiesByCountryId(int id) {
        return new ArrayList<>(Arrays.asList(
                new City(1, "GUS"),
                new City(2, "Vladimir"),
                new City(3, "Murom")
        ));
    }

    @Override
    public List<Country> getCountries() {
        return new ArrayList<>(Arrays.asList(
                new Country(1, "Russia"),
                new Country(2, "USA"),
                new Country(3, "Narnia")));
    }

    @Override
    public City getCityById(int id) {
        return new City(1, "GUS");
    }

    @Override
    public Country getCountryByID(int id) {
        return new Country(1, "Russia");
    }
}
