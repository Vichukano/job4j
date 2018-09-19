package ru.job4j.multithreading.bomberman;

public class Game {

    public Game(BomberMan man) {
        new Thread(man).start();
    }


    public static void main(String[] args) {
        Board board = new Board(3,3);
        BomberMan man = new BomberMan(board, 2, 1);
        new Game(man);
    }
}
