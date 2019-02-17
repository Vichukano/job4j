package ru.job4j.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryStorage implements Storage {
    private final Logger logger = LogManager.getLogger(MemoryStorage.class);
    private List<User> storage = new ArrayList<>();

    @Override
    public void add(User user) {
        this.storage.add(user);
    }

    @Override
    public void delete(User user) {
        this.storage.remove(user);
    }

    @Override
    public User findById(int id) {
        return this.storage.get(id);
    }

    @Override
    public List<User> findAll() {
        return this.storage;
    }
}
