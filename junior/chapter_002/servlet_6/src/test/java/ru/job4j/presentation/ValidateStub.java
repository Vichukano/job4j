package ru.job4j.presentation;

import ru.job4j.logic.Action;
import ru.job4j.logic.Validate;
import ru.job4j.model.User;
import ru.job4j.persistent.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Stub class of ValidateService for testing servlets.
 */
public class ValidateStub implements Validate {
    private final Store store = new DbStoreStub();
    private final Map<Action.Type, Function<User, Boolean>> dispatch = new HashMap<>();


    @Override
    public Function<User, Boolean> add() {
        return this.store::add;
    }

    @Override
    public Function<User, Boolean> update() {
        return user -> {
            boolean result = this.store.findAll().stream().anyMatch(x -> x.getId() == user.getId());
            if (result) {
                this.store.update(user.getId(), user);
            }
            return result;
        };
    }

    @Override
    public Function<User, Boolean> delete() {
        return user -> this.store.delete(user.getId());
    }

    @Override
    public List<User> findAll() {
        return this.store.findAll();
    }

    @Override
    public User findById(int id) {
        return this.store.findById(id);
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public Validate init() {
        load(Action.Type.ADD, add());
        load(Action.Type.DELETE, delete());
        load(Action.Type.UPDATE, update());
        return this;
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }

    @Override
    public boolean isCredential(String login, String password) {
        return false;
    }

    @Override
    public boolean isExist(String login) {
        return false;
    }

    @Override
    public Boolean action(Action.Type action, User user) {
        return this.dispatch.get(action).apply(user);
    }

    public void load(Action.Type type, Function<User, Boolean> handle) {
        this.dispatch.put(type, handle);
    }

    public Store getStore() {
        return store;
    }



}
