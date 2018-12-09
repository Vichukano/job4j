package ru.job4j.persistent;

import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for store users in map.
 * Singleton.
 */
public class MemoryStore implements Store<User> {
    private final static Store STORE = new MemoryStore();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private static int count;

    private MemoryStore() {

    }

    /**
     * Method for getting instance of MemoryStore class.
     *
     * @return instance of MemoryStore.class.
     */
    public static Store getInstance() {
        return STORE;
    }

    /**
     * Method for adding user in map.
     *
     * @param model user object.
     */
    @Override
    public void add(User model) {
        model.setId(count++);
        users.put(model.getId(), model);
    }

    /**
     * Method for return List of stored user objects.
     *
     * @return ArrayList of users objects.
     */
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Method for deleting user object from collection.
     *
     * @param id id of user for deleting.
     */
    @Override
    public void delete(int id) {
        this.users.remove(id);
    }
}
