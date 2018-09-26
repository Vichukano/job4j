package ru.job4j.multithreading.switcher;

import java.util.concurrent.Semaphore;

/**
 * Пример использования Semaphore.
 */
public class Switcher {
    private String string = "";
    volatile boolean flag = true;
    Semaphore semaphore = new Semaphore(1);

    public void addValueToString(int value) {
        string = string + value;
    }

    public String getString() {
        return string;
    }
}

/**
 * Класс описывает добавление в строку значения 1.
 * По заверщению 10 итераций освобождает монитор и прекрашает выполнение метода addValue меняя флаг.
 */
class ProducerOne implements Runnable {
    private Switcher switcher;
    private int value = 1;

    public ProducerOne(Switcher switcher) {
        this.switcher = switcher;
    }

    private synchronized void addValue() {
        while (switcher.flag) {
            try {
                switcher.semaphore.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            for (int i = 0; i < 10; i++) {
                switcher.addValueToString(value);
            }
            switcher.flag = false;
            switcher.semaphore.release();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            addValue();
        }
    }
}

/**
 * Класс описывает добавление в строку значения 2.
 * По заверщению 10 итераций освобождает монитор и прекрашает выполнение метода addValue меняя флаг.
 */
class ProducerTwo implements Runnable {
    private Switcher switcher;
    private int value = 2;

    public ProducerTwo(Switcher switcher) {
        this.switcher = switcher;
    }

    private synchronized void addValue() {
        while (!switcher.flag) {
            try {
                switcher.semaphore.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            for (int i = 0; i < 10; i++) {
                switcher.addValueToString(value);
            }
            switcher.flag = true;
            switcher.semaphore.release();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            addValue();
        }
    }
}
