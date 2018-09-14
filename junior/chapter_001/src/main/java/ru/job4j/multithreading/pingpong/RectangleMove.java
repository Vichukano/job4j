package ru.job4j.multithreading.pingpong;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    private final Rectangle rect;
    private final int limitX;
    private int step;

    /**
     * В конструктор добавлены координаты края площадки и размер шага.
     *
     * @param rect   объект - шарик.
     * @param limitX координата края площадки.
     * @param step   размер шага движения.
     */
    public RectangleMove(Rectangle rect, int limitX, int step) {
        this.rect = rect;
        this.limitX = limitX;
        this.step = step;
    }

    /**
     * Метод останавливает выполнение текущего потока.
     */
    public void stop() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }

    /**
     * Метод реализует логику движения шарика.
     * По достижении края площадки, шарик меняет направление движения.
     */
    private void move() {
        if (this.rect.getX() >= limitX || this.rect.getX() <= 0) {
            step *= -1;
        }
        this.rect.setX(this.rect.getX() + step);
    }

    /**
     * Выполнение движения шарика в отдельном потоке.
     * Реализована проверка на interrupt потока.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            move();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
