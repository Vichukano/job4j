package ru.job4j.multithreading.bomberman;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс описывает реализацию игрового поля.
 * Является общим ресурсом.
 * Поле представляет собой матрицу объектов ReentrantLock.
 */
public class Board {
    private final ReentrantLock[][] board;
    private final int size;
    private final Random random = new Random();

    /**
     * Конструктор игрового поля.
     * Поле представляет собой квадрат.
     *
     * @param size размер поля.
     */
    public Board(int size) {
        this.size = size;
        board = new ReentrantLock[size][size];
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
     * Метод устанавливает блоки на игровом поле.
     */
    public void setBlocks() {
        int blocks = random.nextInt(size / 2);
        for (int i = 0; i < blocks; i++) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            board[x][y].lock();
            System.out.println("Блок установлен в клетке: " + x + " " + y);
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

    /**
     * Геттер для размера поля.
     *
     * @return размер поля.
     */
    public int getSize() {
        return size;
    }
}
