package ru.job4j.multithreading.synchronizedcount;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс - хранилище объектов User.
 */
@ThreadSafe
public class UserStorage implements Storage {
    private List<User> users;

    /**
     * При инициализации создается динамический массив объектов User.
     */
    public UserStorage() {
        this.users = new ArrayList<>();
    }

    /**
     * Метод добавляет объект в хранилище.
     *
     * @param user объект класса содержаешося в хранилище.
     * @return true в случае успешного добавления, иначе false.
     */
    @Override
    public boolean add(User user) {
        boolean result = false;
        if (!users.contains(user)) {
            users.add(user);
            result = true;
        }
        return result;
    }

    /**
     * Метод удаляет объект из хранилища.
     *
     * @param user объект класса содержащегося в хранилище
     * @return true в случае успешного удаления, иначе false.
     */
    @Override
    public boolean delete(User user) {
        boolean result = false;
        if (users.contains(user)) {
            users.remove(user);
            result = true;
        }
        return result;
    }

    /**
     * Метод обнавляет объект в хранилище.
     *
     * @param user объект класса содержащегося в хранилище.
     * @return true в случае успешного обновления, иначе false.
     */
    @Override
    public boolean update(User user) {
        boolean result = false;
        if (users.contains(user)) {
            int index = users.indexOf(user);
            users.set(index, user);
            result = true;
        }
        return result;
    }

    /**
     * Метод возвращает объект, содерщийся в хранилище с заданным id.
     *
     * @param id индекс объекта класса User.
     * @return объекс класса User с переданным индексом.
     * @throws NoSuchElementException   если в хранилище нет объекта с передаваемым индеком.
     * @throws IllegalArgumentException если в метод передается недопустимое значение переменной.
     */
    public User findUserById(int id) {
        User user = new User();
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                user = users.get(i);
                break;
            }
        }
        if (!users.contains(user)) {
            throw new NoSuchElementException();
        }
        return user;
    }

    /**
     * Метод передает amount от одного объекта класса User к другому.
     *
     * @param fromId индекс объекта у кторого отнимается amount.
     * @param toId   индекс объекта которому передается amount.
     * @param amount переменная объекта класса User.
     * @throws NoSuchElementException   если в хранилище нет объекта с передаваемым индеком.
     * @throws IllegalArgumentException если в метод передается недопустимое значение переменной.
     */
    @Override
    public void transfer(int fromId, int toId, int amount) {
        if (fromId < 0 && toId < 0 && amount < 0 && fromId == toId) {
            throw new IllegalArgumentException();
        }
        if (!users.contains(findUserById(fromId)) && !users.contains(findUserById(toId))) {
            throw new NoSuchElementException();
        }
        User fromUser = findUserById(fromId);
        User toUser = findUserById(toId);
        fromUser.setAmount(fromUser.getAmount() - amount);
        toUser.setAmount(toUser.getAmount() + amount);
    }
}

