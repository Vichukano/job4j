package ru.job4j.multithreading.deadlock;


import java.util.concurrent.CountDownLatch;

/**
 * Пример возникновения Deadlock.
 * Два потока одновременно пытаются получить доступ к занятому монитору.
 * Если counts < 2, то deadlock возникает не всегда.
 */
public class Deadlock {

    public static void main(String[] args) {

        Object first = new Object();
        Object second = new Object();
        int counts = 2;
        CountDownLatch latch = new CountDownLatch(counts);

        new Locker(first, second, latch, "one").start();
        new Locker(second, first, latch, "two").start();
    }
}

class Locker extends Thread {
    private CountDownLatch latch;
    private final Object first;
    private final Object second;
    private String name;

    Locker(Object first, Object second, CountDownLatch latch, String name) {
        this.first = first;
        this.second = second;
        this.latch = latch;
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (first) {
            latch.countDown();
            System.out.println("Count down from Thread " + name);
            try {
                latch.await();
                System.out.println("Thread is waiting fo countdown");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Try to finish");
            synchronized (second) {
                System.out.println("Thread finished");
            }
        }
    }
}


