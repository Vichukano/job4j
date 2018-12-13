package ru.job4j.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.User;
import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Класс валидации добавления данных в хранилище пользователей.
 * Синглтон.
 * Применяется Dispatcher Pattern Петра Арсентьева.
 */
public class ValidateService implements Validate {
    private final static Validate SERVICE = new ValidateService();
    private final Store dbStore = DbStore.getInstance();
    private final Logger logger = LogManager.getLogger(ValidateService.class);
    private final Map<Action.Type, Function<User, Boolean>> dispatch = new HashMap<>();

    private ValidateService() {

    }

    public static Validate getInstance() {
        return SERVICE;
    }

    public Function<User, Boolean> add() {
        return user -> {
            boolean result = false;
            if (this.dbStore.findAll().stream().allMatch(x -> !user.equals(x))) {
                result = this.dbStore.add(user);
                logger.debug("User {} added", user);
            }
            return result;
        };
    }

    public Function<User, Boolean> delete() {
        return user -> this.dbStore.delete(user.getId());
    }

    public Function<User, Boolean> update() {
        return user -> {
            boolean result = this.dbStore.findAll().stream().anyMatch(x -> x.getId() == user.getId());
            if (result) {
                this.dbStore.update(user.getId(), user);
                logger.debug("User {} updated", user);
            }
            return result;
        };
    }
    @Override
    public List<User> findAll() {
        return this.dbStore.findAll();
    }

    @Override
    public User findById(int id) {
        return this.dbStore.findById(id);
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User findByLogin(String login) {
        return this.dbStore.findByLogin(login);
    }


    @Override
    public boolean isCredential(String login, String password) {
        boolean exist = false;
        for (User u : findAll()) {
            if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    @Override
    public boolean isExist(String login) {
        boolean exist = false;
        for (User u : findAll()) {
            if (u.getLogin().equals(login)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    @Override
    public Validate init() {
        load(Action.Type.ADD, add());
        load(Action.Type.DELETE, delete());
        load(Action.Type.UPDATE, update());
        return this;
    }

    public void load(Action.Type type, Function<User, Boolean> handle) {
        this.dispatch.put(type, handle);
    }

    @Override
    public Boolean action(Action.Type action, User user) {
        return this.dispatch.get(action).apply(user);
    }
}
