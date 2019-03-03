package ru.job4j.carprice.service;

/**
 * Interface with enum types of actions.
 */
public interface Action {

    enum Type {
        ALL,
        IMAGE,
        LAST,
        RELEVANT
    }
}
