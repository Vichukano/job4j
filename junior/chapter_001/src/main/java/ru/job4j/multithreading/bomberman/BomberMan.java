package ru.job4j.multithreading.bomberman;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Класс описывает поведение игрового персонажа - бомбермен.
 */

public class BomberMan implements Runnable {
    private final Board board;
    private final Cell cell;
    private Random random = new Random();

    /**
     * Конструктор.
     *
     * @param board     игровое поле.
     * @param startCell стартовая позиция персонажа.
     */
    public BomberMan(Board board, Cell startCell) {
        this.board = board;
        cell = startCell;
    }

    /**
     * Метод устанавливает персонажа в клетку с передаваемыми координатами в конструкторе.
     */
    private void setup() {
        board.getBoard()[cell.getX()][cell.getY()].lock();
        System.out.println("Клетка " + cell.getX() + " " + cell.getY() + " заблокирована");
        System.out.println("Бомбермен находится в клетке с координатами: " + cell.getX() + " " + cell.getX());
    }

    /**
     * Метод реализует движение персонажа на игровом поле.
     * Координата клеки, на которую передвинется персонаж выбирается в случайном порядке.
     * При успешном передвижении на новую клетку в this.cell записываются новые коррдинаты.
     */
    private boolean move(Cell source, Cell dist) {
        boolean result = false;
        int srcX = source.getX();
        int srcY = source.getY();
        int dstX = dist.getX();
        int dstY = dist.getY();
        if (!(dstX < 0 || dstX > board.getSize() - 1 || dstY < 0 || dstY > board.getSize() - 1)) {
            try {
                if (board.getBoard()[dstX][dstY].tryLock(500, TimeUnit.MILLISECONDS)) {
                    board.getBoard()[srcX][srcY].unlock();
                    cell.setX(dstX);
                    cell.setY(dstY);
                    System.out.println("Клетка " + srcX + " " + srcY + " разблокирована");
                    System.out.println("Бомбермен передвигается в клетку с координатами: " + dstX + " " + dstY);
                    System.out.println("Клетка " + dstX + " " + dstY + " заблокирована");
                    result = true;
                } else {
                    System.out.println("Этот путь заблокирован");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Выход за пределы игрового поля");
        }
        return result;
    }

    /**
     * Метод возвращает соседнюю клетку в рандомном порядке.
     *
     * @param cell клетка
     * @return новую клетку с рандомной координатой.
     */
    private Cell nextCell(Cell cell) {
        int deltaX = 0;
        int deltaY = 0;
        int randInt = random.nextInt(100);
        if (randInt < 25) {
            deltaX = 1;
        } else if (randInt < 50) {
            deltaY = 1;
        } else if (randInt < 75) {
            deltaX = -1;
        } else if (randInt < 100) {
            deltaY = -1;
        }
        return new Cell(cell.getX() + deltaX, cell.getY() + deltaY);
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
        setup();
        while (!Thread.currentThread().isInterrupted()) {
            move(cell, nextCell(cell));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
