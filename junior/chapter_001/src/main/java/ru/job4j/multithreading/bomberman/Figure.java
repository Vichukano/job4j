package ru.job4j.multithreading.bomberman;

import java.io.IOException;

/**
 * Абстрактный класс, каркас для монтра и бомбермена.
 */
public abstract class Figure {
    public final Board board;
    public final Cell cell;

    /**
     * Конструктор.
     *
     * @param board     игровое поле.
     * @param startCell стартовая позиция персонажа.
     */

    public Figure(Board board, Cell startCell) {
        this.board = board;
        cell = startCell;
    }

    /**
     * Метод устанвливает лок в клетке, где стоит фигура.
     */
    public void setup() {
        board.getBoard()[cell.getX()][cell.getY()].lock();
    }

    /**
     * Каркас метода движения фигуры.
     *
     * @param source клетка, в которой находится фигура.
     * @param dist   клетка куда будет движение
     * @return true, если передвинулась, иначе false.
     * @throws IOException
     */
    abstract boolean move(Cell source, Cell dist) throws IOException;

    /**
     * Метод остановки потока.
     */
    public void stop() {
        Thread.currentThread().interrupt();
        System.out.println("Поток монстра остановлен");
    }

}
