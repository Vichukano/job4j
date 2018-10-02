package ru.job4j.multithreading.bomberman;

import java.util.Random;
import java.util.concurrent.TimeUnit;
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

    public void setup(Cell cell) {
        board[cell.getX()][cell.getY()].lock();
        System.out.println("Клетка с координатами: " + cell.getX() + " " + cell.getY() + " заблокирована.");
    }

    /**
     * Метод реализующий движение персонажей на игровом поле.
     * При вызове, блокирует клетку игрового поля dist и разблокируевает клетку source.
     * После этого задает клетке source новые координаты клетки, на которую передвинулся персонаж.
     * В параметрах установлен флаг - проверка на монстра.
     * Если монстр, то в sout меняется поле name на "монстр".
     *
     * @param source    текущая клетска.
     * @param dist      клетка в которую передвинется персонаж.
     * @param isMonster флаг, является ли монстром.
     * @return true, если персонаж передвинулся, иначе false.
     */
    public boolean move(Cell source, Cell dist, Boolean isMonster) {
        boolean result = false;
        String name = "бомбермен";
        if (isMonster) {
            name = "монстр";
        }
        int srcX = source.getX();
        int srcY = source.getY();
        int dstX = dist.getX();
        int dstY = dist.getY();
        if (!(dstX < 0 || dstX > getSize() - 1 || dstY < 0 || dstY > getSize() - 1)) {
            try {
                System.out.println("Попытка " + name + "a" + " передвинуться в клетку с координатами " + dstX + " " + dstY);
                Thread.sleep(500);
                if (board[dstX][dstY].tryLock(500, TimeUnit.MILLISECONDS)) {
                    board[srcX][srcY].unlock();
                    source.setX(dstX);
                    source.setY(dstY);
                    System.out.println("Клетка " + srcX + " " + srcY + " разблокирована");
                    System.out.println(name + " передвигается в клетку с координатами: " + dstX + " " + dstY);
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
     * Метод устанавливает монстров на игровом поле.
     *
     * @param monsters количество монстров, которое нужно установить.
     */
    public void setMonsters(int monsters) {
        for (int i = 0; i < monsters; i++) {
            new Thread(new Monster(this, new Cell(i, i), "Монстр № " + i)).start();
        }
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
