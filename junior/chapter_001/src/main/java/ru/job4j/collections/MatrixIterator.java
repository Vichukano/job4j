package ru.job4j.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс перебирает все значения многомерного массива.
 * @author Vichukano.
 */
public class MatrixIterator implements Iterator {
    private int[][] values;
    private int rowIndex = 0;
    private int colIndex = 0;
    private int count = 0;

    /**
     * Стандартный конструктор.
     * @param - int[][].
     */
    public MatrixIterator(int[][] values) {
        this.values = values;

    }

    /**
     * Метод считает количество элементов в массиве.
     * Необходимо для реальзации метода hasNext().
     * @return количество элементов в массиве.
     */
    public int size() {
        int size = 0;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                size++;
            }
        }
        return size;
    }

    /**
     * Метод для определения, есть ли следующее значение в массиве.
     * @return true or false.
     */
    @Override
    public boolean hasNext() {
        return count < size();
    }

    /**
     * Метод итерации по массиву.
     * count - счетчик количества возвращенных элементов массива.
     * @return элемент массива.
     * @throws NoSuchElementException если массив пустой.
     */
    @Override
    public Object next() {
        if (values.length != 0) {
            if (colIndex < values[rowIndex].length) {
                count++;
                return values[rowIndex][colIndex++];
            } else {
                count++;
                colIndex = 0;
                return values[++rowIndex][colIndex++];
            }
        } else {
            throw new NoSuchElementException();
        }
    }
}
