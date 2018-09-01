package ru.job4j.collections.tree;

import java.util.Optional;

/**
 * Интерфейс контейнера - дерево с итератором.
 *
 * @param <E> тип передавемого значения контейнеру.
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {

    boolean add(E parent, E child);

    Optional<Node<E>> findBy(E value);
}
