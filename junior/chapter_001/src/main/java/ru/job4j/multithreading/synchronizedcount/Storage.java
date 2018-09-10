package ru.job4j.multithreading.synchronizedcount;


public interface Storage {

    boolean add(User user);

    boolean delete(User user);

    boolean update(User user);

    void transfer(int fromId, int toId, int amount);

}
