package ru.job4j.collections.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс - узел. Хранит value и список дочерних Node.
 *
 * @param <E> тип хранимого value.
 */
public class Node<E extends Comparable<E>> {
    private List<Node<E>> children = new ArrayList<>();
    private E value;

    /**
     * Стандартный конструктор.
     *
     * @param value значение, хранимое в классе Node.
     */
    public Node(E value) {
        this.value = value;
    }

    /**
     * Метод добавляет child Node в конец списка.
     * Реализовано добавление только уникального Node.
     * Проверяется есть ли тако Node в списке дочерних Node.
     *
     * @param child дочерний Node.
     * @return true если узла нет в списке и он добавлен, иначе false.
     */
    public boolean add(Node<E> child) {
        boolean result = false;
        if (!this.children.contains(child)) {
            this.children.add(child);
            result = true;
        }
        return result;
    }

    /**
     * Метод возвращает спиток дочерних узлов.
     *
     * @return список дочерих узлов.
     */
    public List<Node<E>> leaves() {
        return this.children;
    }

    /**
     * Метод возвращает значение, хранимое в узле.
     *
     * @return значение узла.
     */
    public E getValue() {
        return value;
    }

    /**
     * Метод проверяет на эквивалентность значения.
     *
     * @param that передаваемое значение узла.
     * @return true, если эквивалентны, иначе false.
     */
    public boolean eqValue(E that) {
        return this.value.compareTo(that) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node<?> node = (Node<?>) o;
        if (this.value != node.value) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {

        return Objects.hash(children, value);
    }
}