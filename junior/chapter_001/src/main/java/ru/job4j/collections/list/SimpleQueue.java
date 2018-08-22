package ru.job4j.collections.list;

import java.util.Iterator;

public class SimpleQueue<T> implements Iterable<T> {
    private DinamicLinkedContainer<T> dlc;

    public SimpleQueue() {
        this.dlc = new DinamicLinkedContainer<>();
    }

    /**
     * Метод удаляет первое значение из очереди.
     *
     * @return - объект контейнера.
     */
    public T poll() {
        return dlc.removeFirst();
    }

    /**
     * Метод добавляет значение к конец очереди.
     *
     * @param value - значение объекта, добавляемого в контейнер.
     */
    public void push(T value) {
        dlc.add(value);
    }

    /**
     * Реализация метода get.
     *
     * @param index - индекс объекта контейнера
     * @return - значение объекта с заданным индексом.
     */
    public T get(int index) {
        return dlc.get(index);
    }

    /**
     * Реализация итератора.
     *
     * @return класс - итератор коллекции.
     */
    @Override
    public Iterator<T> iterator() {
        return dlc.iterator();
    }
}
