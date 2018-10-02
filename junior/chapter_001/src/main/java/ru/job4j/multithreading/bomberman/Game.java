package ru.job4j.multithreading.bomberman;

/**
 * Класс - main, запускающий игру.
 */
public class Game {

    public static void main(String[] args) {
        Board board = new Board(5);
        board.setBlocks();
        BomberMan bm = new BomberMan(board, new Cell(3, 3));
        Thread bomber = new Thread(bm);
        bomber.start();
        board.setMonsters(1);
    }
}
