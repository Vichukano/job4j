package ru.job4j.collections.iterator.even;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс перебирает все четные значения массива.
 *
 * @author Vichukano.
 * @version $Id$
 * @since 0.1
 */
public class EvenIterator implements Iterator {
    private int[] numbers;
    private int index = 0;

    /**
     * Стандартный конструктор.
     *
     * @param numbers массив.
     */
    public EvenIterator(int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * Метод проверяет является ли число четным.
     *
     * @return true или false.
     */
    public boolean evenCheck() {
        return numbers[index] % 2 == 0;
    }

    /**
     * Метод считает и возвращает количество четных элементов массива.
     *
     * @return количество четных элементов массива.
     */
    public int getSize() {
        int size = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] % 2 == 0) {
                size++;
            }
        }
        return size;
    }

    @Override
    public boolean hasNext() {
        boolean cursor = false;
        while (index < numbers.length) {
            if (this.evenCheck()) {
                cursor = true;
                break;
            }
            else {
                index++;
            }
        }
        return cursor;
    }

    @Override
    public Object next() {
        if (this.hasNext()) {
            return numbers[index++];
        } else {
            throw new NoSuchElementException();
        }
    }
}

