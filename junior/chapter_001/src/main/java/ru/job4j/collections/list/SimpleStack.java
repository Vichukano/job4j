package ru.job4j.collections.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleStack<T> implements Iterable<T> {

    private int top;
    private int modCount;
    private DinamicLinkedContainer<T> dlc;

    public SimpleStack() {
        this.dlc = new DinamicLinkedContainer<>();
    }

    /**
     * Метод удаляет последнее значение из контейнера.
     *
     * @return value - значение удаленного элемента.
     */
    public T poll() {
        T value = dlc.get(--top);
        dlc.remove();
        modCount++;
        return value;
    }

    /**
     * Медол добавляет значение в конец контейнера.
     *
     * @param value - добавляемое в контейнер значение.
     */
    public void push(T value) {
        dlc.add(value);
        top++;
        modCount++;
    }

    /**
     * Метод возвращает значение с заданным индексом.
     * @param index - индекс элемента контейнера.
     * @return - значение с заданным индексом.
     */
    public T get(int index) {
        return dlc.get(index);
    }

    /**
     * Реализация итератора.
     *
     * @return - класс - итератор коллекции.
     */
    @Override
    public Iterator<T> iterator() {
        return new SimpleStackIterator<>();
    }

    private class SimpleStackIterator<T> implements Iterator<T> {
        private int cursor;
        private int expectedModCount;

        public SimpleStackIterator() {
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
