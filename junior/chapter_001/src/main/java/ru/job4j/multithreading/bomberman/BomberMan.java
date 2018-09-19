package ru.job4j.multithreading.bomberman;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс описывает поведение игрового персонажа - бомбермен.
 */

public class BomberMan implements Runnable {
    private final Board board;
    private ReentrantLock position;
    private int x;
    private int y;
    private Random random = new Random();

    /**
     * Конструктор.
     *
     * @param board игровое поле.
     * @param x     начальная координата положения персонажа на поле.
     * @param y     начальная координата положения персонажа на поле.
     */
    public BomberMan(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
    }

    /**
     * Метод устанавливает персонажа в клетку с передаваемыми координатами.
     *
     * @param x координата положения.
     * @param y координата положения.
     */
    private void setup(int x, int y) {
        this.x = x;
        this.y = y;
        position = board.getBoard()[x][y];
        System.out.println("Клетка заблокирована");
        position.lock();
        System.out.println("Бомбермен находится в клетке с координатами: " + x + " " + y);
    }

    /**
     * Метод реализует случайный выбор координаты x или y.
     * 0 == x
     * 1 == y
     *
     * @return 0 или 1.
     */
    private int getCoordinate() {
        return random.nextInt(2);
    }

    /**
     * Метод реализует движение персонажа на игровом поле.
     * Координата клеки, на которую передвинется персонаж выбирается в случайном порядке.
     */
    private void move() {
        int coordinate = getCoordinate();
        if (coordinate == 1) {
            if (y == 0) {
                y++;
            } else if (y == board.getBoard()[x].length - 1) {
                y = y - 1;
            } else {
                y = y + (-1 + random.nextInt(3));
            }
        } else {
            if (x == 0) {
                x++;
            } else if (x == board.getBoard()[y].length - 1) {
                x--;
            } else {
                x = x + (-1 + random.nextInt(3));
            }
        }
        position.unlock();
        System.out.println("Клетка разблокирована");
        position = board.getBoard()[x][y];
        System.out.println("Бомбермен передвигается в клетку с координатами: " + x + " " + y);
        position.lock();
        System.out.println("Клетка заблокирована");
    }

    /**
     * Метод остановки потока.
     */
    public void stop() {
        Thread.currentThread().interrupt();
        System.out.println("Поток остановлен");
    }

    @Override
    public void run() {
        setup(x, y);
        while (!Thread.currentThread().isInterrupted()) {
            move();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
