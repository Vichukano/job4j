package ru.job4j.multithreading.bomberman;

/**
 * Класс - main, запускающий игра.
 */
public class Game {
    private static Board board;

    /**
     * Метод создает монстров на игровом поле.
     *
     * @param creatures
     */
    private static void setCreatures(int creatures) {
        for (int i = 0; i < creatures; i++) {
            new Thread(new Creature(board, new Cell(i, i))).start();
        }
    }

    public static void main(String[] args) {
        board = new Board(10);
        board.setBlocks();
        setCreatures(3);
        BomberMan bm = new BomberMan(board, new Cell(3, 5));
        Thread bomber = new Thread(bm);
        bomber.start();
    }
}
