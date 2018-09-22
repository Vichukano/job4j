package ru.job4j.multithreading.bomberman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;


/**
 * Класс описывает поведение игрового персонажа - бомбермен.
 */

public class BomberMan extends Figure implements Runnable {

    /**
     * Конструктор.
     *
     * @param board     игровое поле.
     * @param startCell стартовая позиция персонажа.
     */
    public BomberMan(Board board, Cell startCell) {
        super(board, startCell);
    }

    /**
     * Метод устанавливает персонажа в клетку с передаваемыми координатами в конструкторе.
     */
    @Override
    public void setup() {
        super.setup();
        System.out.println("Клетка " + cell.getX() + " " + cell.getY() + " заблокирована");
        System.out.println("Бомбермен находится в клетке с координатами: " + cell.getX() + " " + cell.getY());
    }

    /**
     * Метод реализует движение персонажа на игровом поле.
     * Координата клеки, на которую передвинется персонаж выбирается в случайном порядке.
     * При успешном передвижении на новую клетку в this.cell записываются новые коррдинаты.
     */
    @Override
    protected boolean move(Cell source, Cell dist) throws IOException {
        boolean result = false;
        int srcX = source.getX();
        int srcY = source.getY();
        int dstX = dist.getX();
        int dstY = dist.getY();
        if (!(dstX < 0 || dstX > board.getSize() - 1 || dstY < 0 || dstY > board.getSize() - 1)) {
            try {
                System.out.println("Попытка передвинуться в клетку с координатами " + dstX + " " + dstY);
                Thread.sleep(500);
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
                    move(source, bomberMove(source));
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
    public void stop() {
        Thread.currentThread().interrupt();
        System.out.println("Поток бомбермена остановлен");
    }

    @Override
    public void run() {
        setup();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                move(cell, bomberMove(cell));
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
