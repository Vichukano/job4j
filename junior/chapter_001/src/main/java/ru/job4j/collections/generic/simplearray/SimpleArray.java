package ru.job4j.collections.generic.simplearray;

import java.util.Iterator;

/**
 * Универсальная обертка над массивом.
 *
 * @param <T> - любой тип массива.
 */
public class SimpleArray<T> implements Iterable<T> {
    private int index;
    private int size;
    private T model;
    private T[] array;

    /**
     * Стандартный конструктор.
     *
     * @param array - массив.
     * @param size  - размер массива.
     */
    public SimpleArray(T[] array, int size) {
        this.size = size;
        this.array = (T[]) (new Object[size]);
    }

    /**
     * Метод добавляет данные в массив.
     *
     * @param model - объект контейнера.
     * @throws ArrayIndexOutOfBoundsException - бросает при выходе за пределы массива.
     */
    public void add(T model) {
        if (index < size) {
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
        if (index < size) {
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
        if (index == 0) {
            T[] array2 = (T[]) (new Object[array.length - 1]);
            System.arraycopy(array, 1, array2, 0, array2.length);
            array = array2;
        } else if (index == array.length - 1) {
            T[] array2 = (T[]) (new Object[array.length - 1]);
            for (int i = 0; i < array.length - 1; i++) {
                array2[i] = array[i];
            }
            array = array2;
        } else {
            T[] array2 = (T[]) (new Object[array.length - 1]);
            for (int i = 0; i < array.length; i++) {
                if (i > index) {
                    array2[i - 1] = array[i];
                }
                if (i == index) {
                    continue;
                }
                if (i < index) {
                    array2[i] = array[i];
                }
            }
            array = array2;
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
        if (index < size) {
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
            if (cursor < size) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public T next() {
            return (T) array[cursor++];
        }
    }
}
