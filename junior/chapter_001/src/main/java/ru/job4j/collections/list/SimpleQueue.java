package ru.job4j.collections.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleQueue<T> implements Iterable<T> {
    private int modCount;
    private DinamicLinkedContainer<T> dlc;

    public SimpleQueue() {
        this.dlc = new DinamicLinkedContainer<>();
    }

    /**
     * Метод удаляет первое значение из очереди.
     *
     * @return - объект контейнера.
     */
    public T poll() {
        T value = dlc.get(0);
        dlc.removeFirst();
        modCount++;
        return value;
    }

    /**
     * Метод добавляет значение к конец очереди.
     *
     * @param value - значение объекта, добавляемого в контейнер.
     */
    public void push(T value) {
        dlc.add(value);
        modCount++;
    }

    /**
     * Реализация метода get.
     *
     * @param index - индекс объекта контейнера
     * @return - значение объекта с заданным индексом.
     */
    public T get(int index) {
        return dlc.get(index);
    }

    /**
     * Реализация итератора.
     *
     * @return класс - итератор коллекции.
     */
    @Override
    public Iterator<T> iterator() {
        return new SimpleQueueIterator<T>();
    }

    private class SimpleQueueIterator<T> implements Iterator<T> {
        private int cursor;
        private int expectedModCount;

        public SimpleQueueIterator() {
            this.expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (cursor < dlc.getSize()) {
                while (dlc.get(cursor) == null && cursor < dlc.getSize()) {
                    cursor++;
                }
            }
            return cursor < dlc.getSize();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                return (T) dlc.get(cursor++);
            }
        }
    }
}
