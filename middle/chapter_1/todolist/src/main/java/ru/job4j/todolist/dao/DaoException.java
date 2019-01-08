package ru.job4j.todolist.dao;

/**
 * Custom exception class for dao layer.
 */
public class DaoException extends Exception {

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable e) {
        super(message, e);
    }
}
