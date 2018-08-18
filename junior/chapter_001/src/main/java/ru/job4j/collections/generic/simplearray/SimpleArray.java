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
     * @param size - размер массива.
     */
    public SimpleArray(int size) {
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
        if (index < array.length && index >= 0) {
            System.arraycopy(array, index + 1, array, index, array.length - 1 - index);
            array[array.length - 1] = null;
        }
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
        return new SimpleArrayIterator<>();
    }

    private class SimpleArrayIterator<T> implements Iterator<T> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            if (cursor < array.length) {
                while (array[cursor] == null && cursor < array.length) {
                    cursor++;
                }
            }
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
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) array[cursor++];
        }
    }
}
