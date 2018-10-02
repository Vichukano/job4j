package ru.job4j.multithreading.switcher;

import java.util.concurrent.Semaphore;

/**
 * Пример использования Semaphore.
 * Два семафора обеспечивают попеременный доступ потоков к общей переменной.
 * SemaphoreOne с нулевым счетчиком гарантирует, что первым запустится поток ProducerOne.
 * Количество итераций можно изменить в методах run(). По умолчанию 5.
 */
public class Switcher {
    private final StringBuffer sb = new StringBuffer();
    final Semaphore semaphoreOne = new Semaphore(0);
    final Semaphore semaphoreTwo = new Semaphore(1);

    public void addValueToString(int value) {
        sb.append(value);
    }

    public String getString() {
        return sb.toString();
    }
}

