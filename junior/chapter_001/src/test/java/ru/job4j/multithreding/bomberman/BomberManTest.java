package ru.job4j.multithreding.bomberman;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.multithreading.bomberman.Board;
import ru.job4j.multithreading.bomberman.BomberMan;

public class BomberManTest {
    private Board board;
    private BomberMan bomberMan;

    @Before
    public void setup() {
        board = new Board(5,5);
        bomberMan = new BomberMan(board, 0, 0);
    }

    @Test
    public void whenGameStartBomberManShouldMoveCorrectly() throws InterruptedException {
        new Thread(bomberMan).start();
        Thread.sleep(10000);
        bomberMan.stop();
    }
}
