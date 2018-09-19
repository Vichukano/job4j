package ru.job4j.multithreading.bomberman;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс описывает реализацию игрового поля.
 * Является общим ресурсом.
 * Поле представляет собой матрицу объектов ReentrantLock.
 */
public class Board {
    private final ReentrantLock[][] board;

    /**
     * Конструктор игрового поля.
     *
     * @param i размер поля высоте.
     * @param j размер поля по ширине.
     */
    public Board(int i, int j) {
        board = new ReentrantLock[i][j];
        init();

    }

    /**
     * Метод заполняет матрицу объектами.
     */
    private void init() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
    }

    /**
     * Геттер
     *
     * @return матрицу объектов ReentrantLock.
     */
    public ReentrantLock[][] getBoard() {
        return this.board;
    }
}
