package ru.job4j.collections.generic.simplearray;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Универсальная обертка над массивом.
 *
 * @param <T> - любой тип массива.
 */
public class SimpleArray<T> implements Iterable<T> {
    private int index;
    private T[] array;

    /**
     * Стандартный конструктор.
     *
     * @param array - массив.
     * @param size  - размер массива.
     */
    public SimpleArray(T[] array, int size) {
        this.array = (T[]) (new Object[size]);
    }

    /**
     * Метод добавляет данные в массив.
     *
     * @param model - объект контейнера.
     * @throws ArrayIndexOutOfBoundsException - бросает при выходе за пределы массива.
     */
    public void add(T model) {
        if (index < array.length) {
            array[index++] = model;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Меняет данные в массиве по индексу.
     *
     * @param index - индекс.
     * @param model - объект контейнера.
     * @throws ArrayIndexOutOfBoundsException - бросает при выходе за пределы массива.
     */
    public void set(int index, T model) {
        if (index < array.length) {
            array[index] = model;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }

    }

    /**
     * Удаляет объект из массива по индексу. Уменьшает размер массива.
     *
     * @param index - индекс.
     */
    public void delete(int index) {
        int currentSize = array.length;
        for (int i = index; i < currentSize - 1; i++) {
            array[i] = array[i + 1];
        }
        array[currentSize - 1] = null;
        currentSize--;
    }

    /**
     * Метод возвращает значения ячейки массива.
     *
     * @param index - индекс.
     * @return - значение ячейки массива.
     * @throws ArrayIndexOutOfBoundsException - бросает при выходе за пределы массива.
     */
    public T get(int index) {
        if (index < array.length) {
            return array[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Реализация Iterator.
     *
     * @return - объект класса-итератора.
     */
    @Override
    public Iterator<T> iterator() {
        return new SimpleArrayIterator<T>();
    }

    private class SimpleArrayIterator<T> implements Iterator<T> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < array.length;
        }

        /**
         * Метод возвращает следующее значение массива.
         *
         * @return - значение массива по порядку.
         * @throws - NullPointerException, если значение массива null
         * @throws - NoSuchElementException, если вышли за пределы массива.
         */
        @Override
        public T next() {
            if (cursor < array.length && array[cursor] != null) {
                return (T) array[cursor++];
            } else if (cursor < array.length && array[cursor] == null) {
                throw new NullPointerException();
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
