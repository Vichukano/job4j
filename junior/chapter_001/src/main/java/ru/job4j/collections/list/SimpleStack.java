package ru.job4j.collections.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleStack<T> implements Iterable<T> {
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
        return dlc.remove();
    }

    /**
     * Медод добавляет значение в конец контейнера.
     *
     * @param value - добавляемое в контейнер значение.
     */
    public void push(T value) {
        dlc.add(value);
    }

    /**
     * Метод возвращает значение с заданным индексом.
     * @param index - индекс элемента контейнера.
     * @return - значение с заданным индексом.
     */
    public T get(int index) {
        return dlc.get(index);
    }

    public int size() {
        return dlc.getSize();
    }

    /**
     * Реализация итератора.
     *
     * @return - класс - итератор коллекции.
     */
    @Override
    public Iterator<T> iterator() {
        return dlc.iterator();
    }
}
