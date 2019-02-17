package ru.job4j.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImportUser {
    @Autowired
    @Qualifier("jdbc")
    private Storage storage;

    public ImportUser() {

    }

    public void add(User user) {
        this.storage.add(user);
    }

    public void delete(User user) {
        this.storage.delete(user);
    }

    public User findById(int id) {
        return this.storage.findById(id);
    }

    public List<User> findAll() {
        return this.storage.findAll();
    }

}
