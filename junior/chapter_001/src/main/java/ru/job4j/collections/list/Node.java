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
        while (true) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                break;
            }
            if (slow == null || fast == null) {
                break;
            }
            if (slow == fast) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод определяет является ли список замкнутым.
     *
     * @param first - первый Node списка.
     * @return - true если замкнутый, иначе false.
     */
    public boolean hasCycleToo(Node first) {
        Node slow = first;
        Node fast = first;
        while (true) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return false;
            }
            if (slow == null || fast == null) {
                return false;
            }
            if (slow == fast) {
                return true;
            }
        }
    }
}
