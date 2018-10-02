package ru.job4j.multithreading.bomberman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс описывает поведение игрового персонажа - бомбермена.
 */

public class BomberMan implements Runnable {
    private final Cell cell;
    private final Board board;

    /**
     * Конструктор.
     *
     * @param board игровое поле.
     * @param cell  стартовая позиция персонажа.
     */
    public BomberMan(Board board, Cell cell) {
        this.board = board;
        this.cell = cell;
        System.out.println("Бомбер находится в клетке с координатами: " + cell.getX() + " " + cell.getY());
    }

    /**
     * Метод реализующий ввод пользователя для передвижения персонажа.
     *
     * @param cell клетка на поле
     * @return новую клетку, куда передвигается персонаж.
     * @throws IOException
     */
    private Cell bomberMove(Cell cell) throws IOException {
        int deltaX = 0;
        int deltaY = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String direction = reader.readLine();
        switch (direction) {
            case "w":
                deltaX = -1;
                break;
            case "s":
                deltaX = +1;
                break;
            case "a":
                deltaY = -1;
                break;
            case "d":
                deltaY = +1;
                break;
            default:
                break;
        }
        return new Cell(cell.getX() + deltaX, cell.getY() + deltaY);
    }


    @Override
    public void run() {
        board.setup(cell);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                board.move(cell, bomberMove(cell), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
