package ru.job4j.collections.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleQueue<T> implements Iterable<T> {
    private int modCount;
    private int expectedModCount;
    private DinamicContainer<T> dc;

    public SimpleQueue(int size) {
        this.dc = new DinamicContainer<>(size);
    }

    /**
     * Метод удаляет первое значение из очереди.
     *
     * @return - объект контейнера.
     */
    public T poll() {
        T value = dc.get(0);
        dc.deleteFirst();
        modCount++;
        return value;
    }

    /**
     * Метод добавляет значение к конец очереди.
     *
     * @param value - значение объекта, добавляемого в контейнер.
     */
    public void push(T value) {
        dc.add(value);
        modCount++;
    }

    /**
     * Реализация метода get.
     *
     * @param index - индекс объекта контейнера
     * @return - значение объекта с заданным индексом.
     */
    public T get(int index) {
        return dc.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        expectedModCount = modCount;
        return new SimpleQueueIterator<T>();
    }

    private class SimpleQueueIterator<T> implements Iterator<T> {
        private int cursor;

        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (cursor < dc.getSize()) {
                while (dc.get(cursor) == null && cursor < dc.getSize()) {
                    cursor++;
                }
            }
            return cursor < dc.getSize();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                return (T) dc.get(cursor++);
            }
        }
    }
}
