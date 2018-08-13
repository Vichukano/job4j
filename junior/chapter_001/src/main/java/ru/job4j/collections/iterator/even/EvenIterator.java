package ru.job4j.collections.iterator.even;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс перебирает все четные значения массива.
 * @author Vichukano.
 */
public class EvenIterator implements Iterator {
    private int[] numbers;
    private int index = 0;
    private int count = 0;

    /**
     * Стандартный конструктор.
     * @param numbers массив.
     */
    public EvenIterator(int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * Метод проверяет является ли число четным.
     * @return true или false.
     */
    public boolean evenCheck() {
        return numbers[index] % 2 == 0;
    }

    /**
     * Метод считает и возвращает количество четных элементов массива.
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
        return count < getSize();
    }

    @Override
    public Object next() {
        if (numbers.length != 0) {
            if (this.evenCheck()) {
                count++;
                return numbers[index++];
            } else {
                while (index < numbers.length) {
                    if (this.evenCheck()) {
                        count++;
                        return numbers[index++];
                    } else {
                        index++;
                    }
                }
            }
        } else {
            throw new NoSuchElementException();
        }
        throw new NoSuchElementException();
    }
}

