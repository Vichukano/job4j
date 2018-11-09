package ru.job4j.logic;


public interface Action {

    Type type();

    enum Type {
        ADD,
        DELETE,
        UPDATE
    }
}
