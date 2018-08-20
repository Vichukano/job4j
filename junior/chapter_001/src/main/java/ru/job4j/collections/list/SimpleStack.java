package ru.job4j.collections.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleStack<T> implements Iterable<T> {

    private int top;
    private int modCount;
    private int expectedModCount;
    private DinamicContainer<T> dc;

    public SimpleStack(int size) {
        this.dc = new DinamicContainer<>(size);
    }


    public T poll() {
        T value = dc.get(--top);
        dc.delete();
        modCount++;
        return value;
    }


    public void push(T value) {
        dc.add(value);
        top++;
        modCount++;
    }

    public T get(int index) {
        return dc.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        expectedModCount = modCount;
        return new SimpleStackIterator<>();
    }

    private class SimpleStackIterator<T> implements Iterator<T> {
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
