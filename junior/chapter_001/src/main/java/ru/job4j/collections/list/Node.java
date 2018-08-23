package ru.job4j.collections.list;

/**
 * Класс - хранилище данных в связном контейнере.
 *
 * @param <T> - тип хранимого объекта.
 */
public class Node<T> {
    T value;
    public Node<T> next;
    private int count;

    /**
     * Конструктор.
     * Содержит счетчик количества созданных Node в контейнере.
     *
     * @param value - элемент контейнера.
     */
    public Node(T value) {
        this.value = value;
        this.count++;
    }

    /**
     * Пустой конструктор. Нужен для вызова метода hasCycle.
     */
    public Node() {

    }

    /**
     * Метод определяет является ли список замкнутым.
     *
     * @param first - первый Node списка.
     * @return - true если замкнутый, иначе false.
     */
    public boolean hasCycle(Node first) {
        boolean result = false;
        int cursor = 0;
        Node link = first;
        while (link.next != null) {
            link = link.next;
            cursor++;
            if (cursor == count) {
                result = true;
                return result;
            }
        }
        return result;
    }
}
