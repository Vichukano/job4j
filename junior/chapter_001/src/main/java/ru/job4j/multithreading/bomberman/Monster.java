package ru.job4j.multithreading.bomberman;


import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс, реализующий поведение монстра.
 */
public class Monster implements Runnable {
    private final Random random = new Random();
    private final Board board;
    private final Cell cell;

    /**
     * Конструктор.
     *  @param board     игровое поле.
     * @param cell стартовая позиция персонажа.
     */
     public Monster(Board board, Cell cell, String name) {
         this.board = board;
         this.cell = cell;
         System.out.println("Монстр " + name + " установлен в клетке с координатами: "
                 + cell.getX() + " " + cell.getY());
     }

    /**
     * Метод выбирает новое направление движения монстра в случайном порядке.
     *
     * @param cell клетка поля.
     * @return новую клетку, куда будет движение.
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
     * В методе рекурсивно вызывается board.move, если движение монстра не удалось.
     */
    @Override
    public void run() {
        board.setup(cell);
        while (!Thread.currentThread().isInterrupted()) {
            if (!board.move(cell, nextCell(cell), true)) {
                board.move(cell, nextCell(cell), true);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
