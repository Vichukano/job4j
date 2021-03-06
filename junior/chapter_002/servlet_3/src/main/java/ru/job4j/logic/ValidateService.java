package ru.job4j.logic;

import ru.job4j.model.User;
import ru.job4j.persistent.MemoryStore;
import ru.job4j.persistent.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс валидации добавления данных в хранилище пользователей.
 * Синглетон.
 * Применяется Dispatcher Pattern Петра Арсентьева.
 */
public class ValidateService implements Validate {
    private final static ValidateService SERVICE = new ValidateService();
    private final Store memoryStore = MemoryStore.getStoreInstance();
    private final Map<Action.Type, Function<User, Boolean>> dispatch = new HashMap<>();

    private ValidateService() {

    }

    public static ValidateService getInstance() {
        return SERVICE;
    }

    public Function<User, Boolean> add() {
        return user -> {
            boolean result = false;
            if (this.memoryStore.findAll().stream().allMatch(x -> !user.equals(x))) {
                result = this.memoryStore.add(user);
            }
            return result;
        };
    }

    public Function<User, Boolean> delete() {
        return user -> this.memoryStore.delete(user.getId());
    }

    public Function<User, Boolean> update() {
        return user -> {
            boolean result = this.memoryStore.findAll().stream().anyMatch(x -> x.getId() == user.getId());
            if (result) {
                this.memoryStore.update(user.getId(), user);
            }
            return result;
        };
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

    public ValidateService init() {
        load(Action.Type.ADD, add());
        load(Action.Type.DELETE, delete());
        load(Action.Type.UPDATE, update());
        return this;
    }

    public void load(Action.Type type, Function<User, Boolean> handle) {
        this.dispatch.put(type, handle);
    }

    public Boolean action(Action.Type action, User user) {
        return this.dispatch.get(action).apply(user);
    }
}
