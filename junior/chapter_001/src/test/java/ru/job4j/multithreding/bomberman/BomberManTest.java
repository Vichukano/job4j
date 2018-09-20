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
        board = new Board(10);
        cell = new Cell(5, 5);
        bomberMan = new BomberMan(board, cell);
    }

    @Test
    public void whenGameStartBomberManShouldMoveCorrectly() throws InterruptedException {
        new Thread(bomberMan).start();
        Thread.sleep(5000);
        bomberMan.stop();

    }
}
