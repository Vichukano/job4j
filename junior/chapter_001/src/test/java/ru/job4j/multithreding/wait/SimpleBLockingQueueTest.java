package ru.job4j.multithreding.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.multithreading.wait.SimpleBlockingQueue;

/**
 * Реализация шаблона Producer - Consumer.
 */
@ThreadSafe
public class SimpleBLockingQueueTest {

    @ThreadSafe
    private static class Producer implements Runnable {
        @GuardedBy("this")
        private SimpleBlockingQueue<Integer> q;

        public Producer(SimpleBlockingQueue<Integer> q) {
            this.q = q;
        }

        @Override
        public void run() {
            synchronized (this) {
                int i = 0;
                while (i < 10) {
                    q.offer(i);
                    System.out.println("Produce: " + i);
                    i++;
                }
            }
        }
    }

    @ThreadSafe
    private static class Consumer implements Runnable {
        @GuardedBy("this")
        SimpleBlockingQueue<Integer> q;

        public Consumer(SimpleBlockingQueue<Integer> q) {
            this.q = q;
        }

        @Override
        public void run() {
            synchronized (this) {
                int i = 0;
                while (i < 10) {
                    System.out.println("Consume: " + q.poll());
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> q = new SimpleBlockingQueue<>(1);
        Producer p = new Producer(q);
        Consumer c = new Consumer(q);
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
