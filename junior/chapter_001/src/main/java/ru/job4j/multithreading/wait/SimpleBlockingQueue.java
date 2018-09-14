package ru.job4j.multithreading.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<E> {
    @GuardedBy("this")
    private final Queue<E> q;
    private boolean flag = false;
    private final int size;
    private int count;

    public SimpleBlockingQueue(int size) {
        this.q = new LinkedList<>();
        this.size = size;
    }

    /**
     * Метод добавляет значение в конец очереди.
     */
    public void offer(E value) {
        synchronized (this) {
            while (flag || count > size) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notify();
            flag = true;
            q.offer(value);
            count++;
        }
    }

    public E poll() {
        synchronized (this) {
            while (!flag)
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            flag = false;
            notify();
            count--;
            return q.poll();
        }
    }
}
