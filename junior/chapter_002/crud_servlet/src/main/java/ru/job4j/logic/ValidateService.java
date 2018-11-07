package ru.job4j.logic;

import ru.job4j.model.User;
import ru.job4j.persistent.MemoryStore;
import ru.job4j.persistent.Store;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ValidateService implements Validate {
    private final static ValidateService SERVICE = new ValidateService();
    private final Store memoryStore = MemoryStore.getStoreInstance();

    private ValidateService() {

    }

    public static ValidateService getInstance() {
        return SERVICE;
    }

    public boolean add(User user) {
        boolean result = true;
        List<User> users = this.memoryStore.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                result = false;
                break;
            }
        }
        if (result) {
            this.memoryStore.getUsers().add(user);
        }
        return result;
    }

    public boolean delete(int id) {
        return this.memoryStore.delete(id);
    }

    public boolean update(int id, User user) {
        boolean result;
        try {
            this.memoryStore.update(id, user);
            result = true;
        } catch (IndexOutOfBoundsException e) {
            result = false;
        }
        return result;
    }

    public List<User> findAll() {
        return this.memoryStore.findAll();
    }

    public User findById(int id) {
        return this.memoryStore.findById(id);
    }

    public List<User> getUsers() {
        return this.memoryStore.getUsers();
    }
}
