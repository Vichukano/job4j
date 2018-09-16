package ru.job4j.multithreading.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<E> {
    @GuardedBy("this")
    private final Queue<E> q = new LinkedList<>();
    private final int size = 3;
    private int count;

    /**
     * Метод добавляет значение в конец очереди.
     * @param value добавлчемое значение.
     * @throws InterruptedException при переходе потока в состояние wait.
     */
    public void offer(E value) throws InterruptedException {
        synchronized (this) {
            while (count == size) {
                wait();
            }
            q.offer(value);
            count++;
            notify();
        }
    }

    /**
     * Метод извлекает и возвращает значение из начала очереди.
     * @return извлеченное значение.
     * @throws InterruptedException при переходе потока в состояние wait.
     */
    public E poll() throws InterruptedException {
        synchronized (this) {
            while (isEmpty()) {
                wait();
            }
            count--;
            notify();
            return q.poll();
        }
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int getCount() {
        return this.count;
    }
}
