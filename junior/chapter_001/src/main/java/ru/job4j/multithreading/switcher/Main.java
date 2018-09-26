package ru.job4j.multithreading.switcher;

/**
 * Реализация работы класса Switcher.
 */
public class Main {
    public static void main(String[] args) {
        Switcher sw = new Switcher();
        ProducerOne one = new ProducerOne(sw);
        ProducerTwo two = new ProducerTwo(sw);
        Thread t1 = new Thread(one);
        Thread t2 = new Thread(two);
        t1.start();
        t2.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        t2.interrupt();
        System.out.println(sw.getString());
    }
}
