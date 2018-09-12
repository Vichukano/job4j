package ru.job4j.collections.list;

import java.util.Iterator;

public interface Container<E> extends Iterable<E> {

    void add(E value);

    void delete();

    E get(int index);

    int getSize();

    Iterator<E> iterator();

}
