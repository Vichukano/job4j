package ru.job4j.multithreading.bomberman;


import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Класс, реализующий поведение монстра.
 */
public class Creature extends Figure implements Runnable {
    private final Random random = new Random();

    /**
     * Конструктор.
     *
     * @param board     игровое поле.
     * @param startCell стартовая позиция персонажа.
     */

    public Creature(Board board, Cell startCell) {
        super(board, startCell);
    }

    @Override
    public void setup() {
        super.setup();
        System.out.println("Монстр установлен в клетку " + cell.getX() + " " + cell.getY());
    }

    @Override
    protected boolean move(Cell source, Cell dist) {
        boolean result = false;
        int srcX = source.getX();
        int srcY = source.getY();
        int dstX = dist.getX();
        int dstY = dist.getY();
        if (!(dstX < 0 || dstX > board.getSize() - 1 || dstY < 0 || dstY > board.getSize() - 1)) {
            try {
                Thread.sleep(500);
                if (board.getBoard()[dstX][dstY].tryLock(500, TimeUnit.MILLISECONDS)) {
                    board.getBoard()[srcX][srcY].unlock();
                    cell.setX(dstX);
                    cell.setY(dstY);
                    System.out.println("Монстр передвигается в клетку с координатами: " + dstX + " " + dstY);
                    result = true;
                } else {
                    move(source, nextCell(source));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
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
