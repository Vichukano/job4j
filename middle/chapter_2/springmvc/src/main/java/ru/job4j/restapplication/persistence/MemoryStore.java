package ru.job4j.restapplication.persistence;

import org.springframework.stereotype.Component;
import ru.job4j.restapplication.models.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryStore {
    private final List<User> store = new ArrayList<>();

    public MemoryStore() {
        this.fill();
    }

    public void add(User user) {
        this.store.add(user);
    }

    public List<User> findAll() {
        return this.store;
    }

    private void fill() {
        this.store.add(new User("Sam", 18));
        this.store.add(new User("Max", 28));
        this.store.add(new User("Pain", 36));
    }
}
