package ru.job4j.collections.list;

/**
 * Класс - хранилище данных в связном контейнере.
 *
 * @param <T> - тип хранимого объекта.
 */
public class Node<T> {
    T value;
    public Node<T> next;

    /**
     * Конструктор.
     *
     * @param value - элемент контейнера.
     */
    public Node(T value) {
        this.value = value;
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
        Boolean result = false;
        Node slow = first;
        Node fast = first;
        while (fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                result = true;
                break;
            }
        }
        return result;
    }
}
