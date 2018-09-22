package ru.job4j.multithreding.bomberman;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.multithreading.bomberman.Board;
import ru.job4j.multithreading.bomberman.BomberMan;
import ru.job4j.multithreading.bomberman.Cell;


public class BomberManTest {
    private Board board;
    private BomberMan bomberMan;
    private Cell cell;

    @Before
    public void setup() {
        board = new Board(5);
        cell = new Cell(1, 1);
        bomberMan = new BomberMan(board, cell);
        board.getBoard()[0][2].lock();
        board.getBoard()[1][2].lock();
        board.getBoard()[2][2].lock();
    }

    @Test
    public void whenGameStartBomberManShouldMoveCorrectly() throws InterruptedException {

    }
}
