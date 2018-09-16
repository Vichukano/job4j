package ru.job4j.multithreding.wait;

import ru.job4j.multithreading.wait.Consumer;
import ru.job4j.multithreading.wait.Producer;
import ru.job4j.multithreading.wait.SimpleBlockingQueue;


/**
 * Реализация шаблона Producer - Consumer.
 */
public class SimpleBLockingQueueTest {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> q = new SimpleBlockingQueue<>();
        Producer p = new Producer(q);
        Consumer c = new Consumer(q);
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);
        t1.start();
        t2.start();
    }
}
