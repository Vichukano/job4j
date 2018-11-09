package ru.job4j.persistent;

import ru.job4j.model.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс - хранилище пользователей.
 * Синглетон.
 */
public class MemoryStore implements Store {
    private final static MemoryStore STORE = new MemoryStore();
    private final List<User> users = new CopyOnWriteArrayList<>();

    private MemoryStore() {

    }

    public static MemoryStore getStoreInstance() {
        return STORE;
    }


    @Override
    public boolean add(User user) {
        return users.add(user);
    }

    @Override
    public User update(int id, User user) throws ArrayIndexOutOfBoundsException {
        User curUser = findById(id);
        this.users.set(this.users.indexOf(curUser), user);
        user.setId(curUser.getId());
        return user;
    }

    @Override
    public boolean delete(int id) {
        User user = findById(id);
        return this.users.remove(user);
    }

    @Override
    public List<User> findAll() {
        List<User> tmp = users;
        return tmp;
    }

    @Override
    public User findById(int id) {
        User user = null;
        for (int i = 0; i < this.users.size(); i++) {
            if (users.get(i).getId() == id) {
                user = users.get(i);
                break;
            }
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        return this.users;
    }
}
