package ru.job4j.collections.iterator.convert;

import java.util.Iterator;
import java.util.*;

/**
 * Класс итератор итераторов.
 *
 * @param <Integer>
 */
public class IteratorOfIterators<Integer> implements Iterator {
    private Iterator<Iterator<Integer>> iterator;
    private Iterator<Integer> currentIterator = null;

    /**
     * Стандартный конструктор.
     *
     * @param iterator - Iterator<Iterator<Integer>>.
     */
    public IteratorOfIterators(Iterator<Iterator<Integer>> iterator) {
        this.iterator = iterator;
    }

    /**
     * Метод, реализующий итератор для Iterator<Iterator>.
     *
     * @param it - Итератор итератора целых чисел.
     * @return Итератор.
     */
    public IteratorOfIterators<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new IteratorOfIterators<>(it);
    }

    @Override
    public boolean hasNext() {
        selectCurrentIterator();
        return (currentIterator != null && currentIterator.hasNext());
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return currentIterator.next();
    }

    /**
     * Метод, выбирает текущий итератор.
     * Если текущий итератор существует и имеет следующие значения, то пропускаем и проверяем следующий итератор.
     * Иначе переходим к следующему итератору.
     */
    private void selectCurrentIterator() {
        if (currentIterator != null && currentIterator.hasNext()) {
            return; // Пустой return в методе void пропускает шаг.
        } else {
            currentIterator = null;
            while (iterator.hasNext()) {
                Iterator<Integer> nextIterator = iterator.next();
                if (nextIterator.hasNext()) {
                    currentIterator = nextIterator;
                    break;
                }
            }
        }
    }
}